package open.pay.center.api.daifu;

import open.pay.center.api.PayChannelEnum;
import open.pay.center.api.PayManager;
import open.pay.center.api.config.ApiConfig;
import open.pay.center.api.daifu.request.DaifuQueryRequest;
import open.pay.center.api.daifu.request.DaifuSubmitBatchRequest;
import open.pay.center.api.daifu.request.DaifuSubmitRequest;
import open.pay.center.api.daifu.response.DaifuQueryResponse;
import open.pay.center.api.daifu.response.DaifuSubmitResponse;
import open.pay.center.baofu.daifu.request.BaofuQueryTwoStepDaifuRequest;
import open.pay.center.baofu.daifu.request.BaofuSubmitTwoStepDaifuRequest;
import open.pay.center.baofu.daifu.response.BaofuQueryTwoStepDaifuResponse;
import open.pay.center.baofu.daifu.response.BaofuSubmitTwoStepDaifuResponse;
import open.pay.center.baofu.daifu.vo.TwoStepDaifuItemVo;
import open.pay.center.baofu.exception.BaofuException;
import open.pay.center.core.daifu.way.TwoStepDaifu;
import open.pay.center.core.model.Money;
import open.pay.center.core.model.ResponseStatus;

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


    public DaifuHandlerAdapter(){};
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

    public DaifuSubmitResponse submitBatch(PayChannelEnum payChannelEnum, DaifuSubmitBatchRequest request){
        //根据渠道不同构建不同的参数信息
        DaifuSubmitResponse response = null;
        switch (payChannelEnum){
            case BAOFU:
                response = this.executeBaofuDaifuSubmitBatch(request);
                break;
            case UNION:
                break;
            default:
        }
        return response;
    }


    public int maxBatchSize(PayChannelEnum payChannelEnum){
        int maxSize = 1;
        switch (payChannelEnum){
            case BAOFU:
                maxSize = 5;
                break;
            case UNION:
                maxSize = 1;
                break;
            default:
        }
        return maxSize;
    }

    private DaifuSubmitResponse executeBaofuDaifuSubmitBatch(DaifuSubmitBatchRequest request) {
        TwoStepDaifu handler = PayManager.getInstance().getTwoStepDaifu(PayChannelEnum.BAOFU);
        //1.构建请求参数
        BaofuSubmitTwoStepDaifuRequest baofuSubmitTwoStepDaifuRequest = this.buildBaofuDaifuSubmitRequest(null,request);
        //2.执行
        BaofuSubmitTwoStepDaifuResponse submitTwoStepDaifuResponse = handler.submitTwoStepDaifu(baofuSubmitTwoStepDaifuRequest);


        return null;
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
                BaofuSubmitTwoStepDaifuRequest submitBaofuRequest = this.buildBaofuDaifuSubmitRequest(request,null);
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
    private BaofuSubmitTwoStepDaifuRequest buildBaofuDaifuSubmitRequest(DaifuSubmitRequest request,DaifuSubmitBatchRequest batchRequest) {
        BaofuSubmitTwoStepDaifuRequest submitTwoStepDaifuRequest = new BaofuSubmitTwoStepDaifuRequest();
        submitTwoStepDaifuRequest.setUrl(config.getBfDaifuSubmitUrl());//请求URL
        submitTwoStepDaifuRequest.setConnectionTimeout(config.getBfDaifuSubmitHttpConnectionTimeOut());//创建链接超时时间
        submitTwoStepDaifuRequest.setReadTimeout(config.getBfDaifuSubmitHttpReadTimeout());//读取数据超时时间
        submitTwoStepDaifuRequest.setMerInfo(config.getBaofuMerInoList().get(config.getBfDaifuSubmitMerId() + config.getBfDaifuSubmitTerminal()));//商户信息
        submitTwoStepDaifuRequest.setEncrpyt(ApiConfig.ENV_TEST.equals(config.getEnv()) ? false : true);
        submitTwoStepDaifuRequest.setEncryptPassword(config.getLogEncryptPassword());
        List<TwoStepDaifuItemVo> items = new ArrayList<TwoStepDaifuItemVo>();//提交内容
        //单笔提交
        if(request == null && batchRequest != null){
            batchRequest.getItemList().add(request);
        }else{
            batchRequest = new DaifuSubmitBatchRequest();
            List<DaifuSubmitRequest> list = new ArrayList<DaifuSubmitRequest>();
            if(request != null){
                list.add(request);
            }
            batchRequest.setItemList(list);
        }
        if(batchRequest.getItemList().size() > this.maxBatchSize(PayChannelEnum.BAOFU)){
            throw new BaofuException("超过最大提交参数"+this.maxBatchSize(PayChannelEnum.BAOFU));
        }
        for (DaifuSubmitRequest daifuSubmitRequest : batchRequest.getItemList()) {
            Money amount = Money.newByFen(daifuSubmitRequest.getAmount());//分转换为元
            TwoStepDaifuItemVo item = new TwoStepDaifuItemVo(daifuSubmitRequest.getOrderNo(),amount.getYuan()+"",daifuSubmitRequest.getUserName(),daifuSubmitRequest.getCardNo(),daifuSubmitRequest.getBankName());
            item.setToProName(daifuSubmitRequest.getProvince()); //收款人开户行省名
            item.setToCityName(daifuSubmitRequest.getCity());//收款人开户行市名
            item.setToAccDept(daifuSubmitRequest.getSubBank());//收款人开户行机构名
            item.setTransSummary(daifuSubmitRequest.getRemark());//摘要
            items.add(item);
        }
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
        BaofuSubmitTwoStepDaifuRequest baofuSubmitTwoStepDaifuRequest = this.buildBaofuDaifuSubmitRequest(request,null);
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
        queryTwoStepDaifuRequest.setEncrpyt(ApiConfig.ENV_TEST.equals(config.getEnv()) ? false : true);
        queryTwoStepDaifuRequest.setEncryptPassword(config.getLogEncryptPassword());
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
        ResponseStatus transStatus = null;
        if(item != null){
            amount = Money.newByYuan(item.getTransMoney()).getFen() + "";
            channelOrderNo = item.getTransOrderid();
            cardNo = item.getToAccNo();
            transStatus = item.getTransState();
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
        daifuQueryResponse.setTransStatus(transStatus);
        return daifuQueryResponse;
    }

    public ApiConfig getConfig() {
        return config;
    }

    public void setConfig(ApiConfig config) {
        this.config = config;
    }
}
