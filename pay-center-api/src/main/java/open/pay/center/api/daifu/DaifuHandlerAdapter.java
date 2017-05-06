package open.pay.center.api.daifu;

import open.pay.center.api.PayChannelEnum;
import open.pay.center.api.PayManager;
import open.pay.center.api.config.ApiConfig;
import open.pay.center.api.daifu.request.DaifuQueryRequest;
import open.pay.center.api.daifu.request.DaifuSubmitRequest;
import open.pay.center.api.daifu.response.DaifuQueryResponse;
import open.pay.center.api.daifu.response.DaifuSubmitResponse;
import open.pay.center.baofu.daifu.request.BaofuSubmitTwoStepDaifuRequest;
import open.pay.center.baofu.daifu.response.BaofuSubmitTwoStepDaifuResponse;
import open.pay.center.baofu.daifu.vo.TwoStepDaifuItemVo;
import open.pay.center.core.daifu.way.TwoStepDaifu;
import open.pay.center.core.model.Money;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * User: hyman
 * Date: 2017/5/5 0005
 * Time: 17:48
 * Email: qyuhy@qq.com
 */
public class DaifuHandlerAdapter{
    private ApiConfig config;

    public DaifuHandlerAdapter(ApiConfig config){
        this.config = config;
    }

    public DaifuSubmitResponse submit(PayChannelEnum payChannelEnum,DaifuSubmitRequest request){
        //根据渠道不同构建不同的参数信息
        DaifuSubmitResponse response = null;
        switch (payChannelEnum){
            case BAOFU:
                response = this.buildBaofuDaifuSubmit(request);
                break;
            case UNION:
                response = this.buildUnionDaifuSubmit(request);
                break;
            default:
        }
        return response;
    }

    /**
     * 把请求对象转换为字符串；
     * @param payChannelEnum
     * @param request
     * @return
     */
    public String formatDaifuSubmitRequest(PayChannelEnum payChannelEnum,DaifuSubmitRequest request){
        String response = null;
        switch (payChannelEnum){
            case BAOFU:
                BaofuSubmitTwoStepDaifuRequest submitBaofuRequest = this.buildBaofuDaifuSubmitRequest(request);
                response = submitBaofuRequest.formatJsonString();
                break;
            case UNION:
                break;
            default:
        }
        return response;
    }

    /**
     * 请求对象转换为BaofuSubmitTwoStepDaifuRequest
     * @param request
     * @return
     */
    private BaofuSubmitTwoStepDaifuRequest buildBaofuDaifuSubmitRequest(DaifuSubmitRequest request) {
        BaofuSubmitTwoStepDaifuRequest submitTwoStepDaifuRequest = new BaofuSubmitTwoStepDaifuRequest();
        submitTwoStepDaifuRequest.setUrl(config.getBfDaifuSubmitUrl());//请求URL
        submitTwoStepDaifuRequest.setConnectionTimeout(config.getBfDaifuSubmitHttpConnectionTimeOut());//创建链接超时时间
        submitTwoStepDaifuRequest.setReadTimeout(config.getBfDaifuSubmitHttpReadTimeout());//读取数据超时时间
        submitTwoStepDaifuRequest.setMerInfo(config.getBaofuMerInoList().get(config.getBfDaifuSubmitMerId() + config.getBfDaifuSubmitTerminal()));//商户信息
        List<TwoStepDaifuItemVo> items = new ArrayList<TwoStepDaifuItemVo>();//提交内容
        Money amount = Money.newByFen(request.getAmount());//分转换为元
        TwoStepDaifuItemVo item = new TwoStepDaifuItemVo(request.getOrderNo(),amount.getYuan()+"",request.getUserName(),request.getCardNo(),request.getBankName());
        item.setToProName(request.getProvince()); //收款人开户行省名
        item.setToCityName(request.getCity());//收款人开户行市名
        item.setToAccDept(request.getSubBank());//收款人开户行机构名
        item.setTransSummary(request.getRemark());//摘要
        items.add(item);
        submitTwoStepDaifuRequest.setItems(items);
        return submitTwoStepDaifuRequest;
    }

    private DaifuSubmitResponse buildBaofuDaifuSubmit(DaifuSubmitRequest request) {
        TwoStepDaifu handler = PayManager.getInstance().getTwoStepDaifu(PayChannelEnum.BAOFU);
        //1.构建请求参数
        BaofuSubmitTwoStepDaifuRequest baofuSubmitTwoStepDaifuRequest = this.buildBaofuDaifuSubmitRequest(request);
        //2.执行
        BaofuSubmitTwoStepDaifuResponse submitTwoStepDaifuResponse = handler.submitTwoStepDaifu(baofuSubmitTwoStepDaifuRequest);
        //3.转换请求为统一的参数;
        Map<String, TwoStepDaifuItemVo> items = submitTwoStepDaifuResponse.getItems();
        TwoStepDaifuItemVo item = null;
        if(items != null && items.size() == 1){
            Set<Map.Entry<String, TwoStepDaifuItemVo>> entryseSet = items.entrySet();
            for (Map.Entry<String, TwoStepDaifuItemVo> entry : entryseSet) {
                item = entry.getValue();
            }
        }
        String amount = null;
        String orderNo = null;
        if(item != null){
            amount = item.getTransMoney();
            orderNo = item.getTransNo();
        }
        DaifuSubmitResponse response = new DaifuSubmitResponse(
                submitTwoStepDaifuResponse.getTipStatus(),
                submitTwoStepDaifuResponse.getTip(),
                submitTwoStepDaifuResponse.getPlainResponse(),
                submitTwoStepDaifuResponse.getStatus(),amount,orderNo);
        response.setEntry(item);
        if(item != null){
            response.setCardNo(item.getToAccNo());
            response.setChannelBatchid(item.getTransBatchid());
            response.setChannelOrderNo(item.getTransOrderid());
        }
        return response;
    }

    private DaifuSubmitResponse buildUnionDaifuSubmit(DaifuSubmitRequest request) {

        return null;
    }

    public DaifuQueryResponse query(PayChannelEnum payChannelEnum,DaifuQueryRequest request){
        return null;
    }

}
