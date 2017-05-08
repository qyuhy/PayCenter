package open.pay.center.api.daifu;

import open.pay.center.api.PayChannelEnum;
import open.pay.center.api.PayManager;
import open.pay.center.api.config.ApiConfig;
import open.pay.center.api.daifu.request.DaifuQueryRequest;
import open.pay.center.api.daifu.request.DaifuSubmitRequest;
import open.pay.center.api.daifu.response.DaifuQueryResponse;
import open.pay.center.api.daifu.response.DaifuSubmitResponse;
import open.pay.center.baofu.daifu.request.BaofuQueryTwoStepDaifuRequest;
import open.pay.center.baofu.daifu.request.BaofuSubmitTwoStepDaifuRequest;
import open.pay.center.baofu.daifu.response.BaofuQueryTwoStepDaifuResponse;
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
                response = this.executeBaofuDaifuSubmit(request);
                break;
            case UNION:
                response = this.executeUnionDaifuSubmit(request);
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

    /**
     * 执行宝付提交信息;
     * @param request
     * @return
     */
    private DaifuSubmitResponse executeBaofuDaifuSubmit(DaifuSubmitRequest request) {
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
            amount = Money.newByYuan(item.getTransMoney()).getFen() + "";
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

    /**
     *执行银联代付请求
     * @param request
     * @return
     */
    private DaifuSubmitResponse executeUnionDaifuSubmit(DaifuSubmitRequest request) {
        return null;
    }

    public DaifuQueryResponse query(PayChannelEnum payChannelEnum,DaifuQueryRequest request){
        //根据渠道不同构建不同的参数信息
        DaifuQueryResponse response = null;
        switch (payChannelEnum){
            case BAOFU:
                response = this.executeBaofuDaifuQuery(request);
                break;
            case UNION:
                response = this.executeUnionDaifuQuery(request);
                break;
            default:
        }
        return response;
    }

    /**
     * 执行银联代付请求
     * @param daifuQueryRequest
     * @return
     */
    private DaifuQueryResponse executeUnionDaifuQuery(DaifuQueryRequest daifuQueryRequest) {
        return null;
    }

    /**
     * 构建宝付代付请求
     * @param daifuQueryRequest
     * @return
     */
    private BaofuQueryTwoStepDaifuRequest buildBaofuDaifuQueryRequest(DaifuQueryRequest daifuQueryRequest) {
        BaofuQueryTwoStepDaifuRequest queryTwoStepDaifuRequest = new BaofuQueryTwoStepDaifuRequest();
        queryTwoStepDaifuRequest.setUrl(config.getBfDaifuQueryUrl());//请求URL
        queryTwoStepDaifuRequest.setConnectionTimeout(config.getBfDaifuQueryHttpConnectionTimeOut());//创建链接超时时间
        queryTwoStepDaifuRequest.setReadTimeout(config.getBfDaifuQueryHttpReadTimeout());//读取数据超时时间
        queryTwoStepDaifuRequest.setMerInfo(config.getBaofuMerInoList().get(config.getBfDaifuQueryMerId() + config.getBfDaifuQueryTerminal()));//商户信息
        List<TwoStepDaifuItemVo> items = new ArrayList<TwoStepDaifuItemVo>();//提交内容
        TwoStepDaifuItemVo item = new TwoStepDaifuItemVo(daifuQueryRequest.getOrderNo(),daifuQueryRequest.getBatchId());
        items.add(item);
        queryTwoStepDaifuRequest.setParams(items);
        return queryTwoStepDaifuRequest;
    }

    /**
     * 格式化代付请求参数，方便客户端储存请求参数
     * @param payChannelEnum
     * @param request
     * @return
     */
    public String formatDaifuQueryRequest(PayChannelEnum payChannelEnum,DaifuQueryRequest request){
        String response = null;
        switch (payChannelEnum){
            case BAOFU:
                BaofuQueryTwoStepDaifuRequest queryTwoStepDaifuRequest = this.buildBaofuDaifuQueryRequest(request);
                response = queryTwoStepDaifuRequest.formatJsonString();
                break;
            case UNION:
                break;
            default:
        }
        return response;
    }

    /**
     * 执行宝付代付查询请求
     * @param daifuQueryRequest
     * @return
     */
    private DaifuQueryResponse executeBaofuDaifuQuery(DaifuQueryRequest daifuQueryRequest) {
        TwoStepDaifu handler = PayManager.getInstance().getTwoStepDaifu(PayChannelEnum.BAOFU);
        //1.构建请求
        BaofuQueryTwoStepDaifuRequest queryTwoStepDaifuRequest = this.buildBaofuDaifuQueryRequest(daifuQueryRequest);
        //2.执行请求
        BaofuQueryTwoStepDaifuResponse queryTwoStepDaifuResponse = handler.queryTwoStepDaifu(queryTwoStepDaifuRequest);
        //3.统一构建返回值
        Map<String, TwoStepDaifuItemVo> items = queryTwoStepDaifuResponse.getItems();
        TwoStepDaifuItemVo item = null;
        if(items != null && items.size() == 1){
            Set<Map.Entry<String, TwoStepDaifuItemVo>> entryseSet = items.entrySet();
            for (Map.Entry<String, TwoStepDaifuItemVo> entry : entryseSet) {
                item = entry.getValue();
            }
        }
        String amount = null;
        String channelOrderNo = null;
        String cardNo = null;
        if(item != null){
            amount = Money.newByYuan(item.getTransMoney()).getFen() + "";
            channelOrderNo = item.getTransOrderid();
            cardNo = item.getToAccNo();
        }
        DaifuQueryResponse daifuQueryResponse = new DaifuQueryResponse(
                queryTwoStepDaifuResponse.getTipStatus(),
                queryTwoStepDaifuResponse.getTip(),
                queryTwoStepDaifuResponse.getPlainResponse(),
                queryTwoStepDaifuResponse.getStatus(),
                amount);
        daifuQueryResponse.setChannelOrderNo(channelOrderNo);
        daifuQueryResponse.setCardNo(cardNo);
        daifuQueryResponse.setEntry(item);
        return daifuQueryResponse;
    }

}
