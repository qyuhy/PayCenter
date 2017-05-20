package open.pay.center.api.daifu;

import open.pay.center.api.PayChannelEnum;
import open.pay.center.api.PayManager;
import open.pay.center.api.config.ApiConfig;
import open.pay.center.api.daifu.request.DaifuQueryRequest;
import open.pay.center.api.daifu.request.DaifuSubmitBatchRequest;
import open.pay.center.api.daifu.request.DaifuSubmitRequest;
import open.pay.center.api.daifu.response.DaifuQueryResponse;
import open.pay.center.api.daifu.response.DaifuSubmitBatchResponse;
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
import open.pay.center.union.daifu.request.UnionSubmitTwoStepDaifuRequest;
import open.pay.center.union.daifu.response.UnionSubmitTwoStepDaifuResponse;
import open.pay.center.union.daifu.vo.UnionDaifuItem;
import open.pay.center.union.exception.UnionException;

import java.text.SimpleDateFormat;
import java.util.*;

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

    public DaifuSubmitBatchResponse submitBatch(PayChannelEnum payChannelEnum, DaifuSubmitBatchRequest request){
        //根据渠道不同构建不同的参数信息
        DaifuSubmitBatchResponse response = null;
        switch (payChannelEnum){
            case BAOFU:
                response = this.executeBaofuDaifuSubmitBatch(request);
                break;
            case UNION:
                response = this.executeUnionDaifuSubmitBatch(request);
                break;
            default:
        }
        return response;
    }

    private DaifuSubmitBatchResponse executeUnionDaifuSubmitBatch(DaifuSubmitBatchRequest request) {
        TwoStepDaifu handler = PayManager.getInstance().getTwoStepDaifu(PayChannelEnum.UNION);
        //1.构建请求参数
        UnionSubmitTwoStepDaifuRequest realRequest = this.buildUnionDaifuSubmitRequest(null,request);
        //2.执行
        UnionSubmitTwoStepDaifuResponse unionResponse = handler.submitTwoStepDaifu(realRequest);
        UnionDaifuItem data = unionResponse.getData();
        //3.转换为统一的响应对象;
        DaifuSubmitBatchResponse response = new DaifuSubmitBatchResponse(
                unionResponse.getTipStatus(),
                unionResponse.getTip(),
                unionResponse.getPlainResponse(),
                unionResponse.getStatus());
        List<DaifuSubmitResponse> items = new ArrayList<>(this.maxBatchSize(PayChannelEnum.UNION));
        DaifuSubmitResponse item = new DaifuSubmitResponse(
                unionResponse.getTipStatus(),
                unionResponse.getTip(),
                unionResponse.getPlainResponse(),
                unionResponse.getStatus(),
                data.getTransAmt(),
                data.getMerSeqId());
        item.setEntry(data);
        item.setCardNo(data.getCardNo());
        item.setChannelOrderNo(data.getCpSeqId());
        item.setTransStatus(data.getTransState());
        items.add(item);
        response.setItemList(items);
        return response;
    }

    /**
     * 由DaifuSubmitBatchRequest转换为UnionSubmitTwoStepDaifuRequest
     * @param request
     * @return
     */
    private UnionSubmitTwoStepDaifuRequest buildUnionDaifuSubmitRequest(DaifuSubmitRequest singleRequest ,DaifuSubmitBatchRequest request) {
        UnionSubmitTwoStepDaifuRequest retValue = new UnionSubmitTwoStepDaifuRequest();
        retValue.setUrl(config.getUnionDaifuSubmitUrl());//请求URL
        retValue.setMerId(config.getUnionDaifuSubmitMerId());//请求商户号
        retValue.setConnectionTimeout(config.getUnionDaifuSubmitHttpConnectionTimeOut());//创建链接超时时间
        retValue.setReadTimeout(config.getUnionDaifuSubmitHttpReadTimeout());//读取数据超时时间
        retValue.setMerInfo(config.getUnionMerInfoList().get(config.getUnionDaifuSubmitMerId()));//商户信息
        retValue.setEncrpyt(ApiConfig.ENV_TEST.equals(config.getEnv()) ? false : true);
        retValue.setEncryptPassword(config.getLogEncryptPassword());
        DaifuSubmitRequest item = null;
        if(request != null){
            if(request.getItemList() == null){
                throw UnionException.PARAM_ERROR.newInstance("UnionSubmitTwoStepDaifuRequest[ItemList]为空");
            }
            int maxBatchSize = this.maxBatchSize(PayChannelEnum.UNION);
            if(request.getItemList().size() > maxBatchSize){
                throw UnionException.PARAM_ERROR.newInstance("超过了批量[%s]大小限制",maxBatchSize);
            }
            item = request.getItemList().get(0);
        }else{
            item = singleRequest;
        }
        retValue.setMerDate(item.getDate() == null ? new SimpleDateFormat("yyyyMMdd").format(new Date()) : new SimpleDateFormat("yyyyMMdd").format(item.getDate()));
        retValue.setMerSeqId(item.getOrderNo());
        retValue.setCardNo(item.getCardNo());
        retValue.setUsrName(item.getUserName());
        retValue.setOpenBank(item.getBankName());
        retValue.setProv(item.getProvince());
        retValue.setCity(item.getCity());
        retValue.setTransAmt(item.getAmount());
        retValue.setPurpose(item.getRemark());
        retValue.setFlag(item.isPersonal() ? UnionSubmitTwoStepDaifuRequest.FLAG_PRIVATE : UnionSubmitTwoStepDaifuRequest.FLAG_PUBLIC);
        return retValue;
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

    /**
     * 宝付批量提交
     * @param request
     * @return
     */
    private DaifuSubmitBatchResponse executeBaofuDaifuSubmitBatch(DaifuSubmitBatchRequest request) {
        TwoStepDaifu handler = PayManager.getInstance().getTwoStepDaifu(PayChannelEnum.BAOFU);
        //1.构建请求参数
        BaofuSubmitTwoStepDaifuRequest baofuSubmitTwoStepDaifuRequest = this.buildBaofuDaifuSubmitRequest(null,request);
        //2.执行
        BaofuSubmitTwoStepDaifuResponse submitTwoStepDaifuResponse = handler.submitTwoStepDaifu(baofuSubmitTwoStepDaifuRequest);
        //3.构建统一返回值
        DaifuSubmitBatchResponse response = new DaifuSubmitBatchResponse(
                submitTwoStepDaifuResponse.getTipStatus(),
                submitTwoStepDaifuResponse.getTip(),
                submitTwoStepDaifuResponse.getPlainResponse(),
                submitTwoStepDaifuResponse.getStatus());
        response.setEntry(submitTwoStepDaifuResponse.getItems());

        Map<String, TwoStepDaifuItemVo> items = submitTwoStepDaifuResponse.getItems();
        List<DaifuSubmitResponse> list = null;

        if(items != null && items.size()>0){
            list = new ArrayList<DaifuSubmitResponse>(items.size());
            Set<Map.Entry<String, TwoStepDaifuItemVo>> entryseSet = items.entrySet();
            for (Map.Entry<String, TwoStepDaifuItemVo> entry : entryseSet) {
                TwoStepDaifuItemVo twoStepDaifuItemVo = entry.getValue();
                DaifuSubmitResponse item = new DaifuSubmitResponse(
                        submitTwoStepDaifuResponse.getTipStatus(),
                        submitTwoStepDaifuResponse.getTip(),
                        submitTwoStepDaifuResponse.getPlainResponse(),
                        submitTwoStepDaifuResponse.getStatus(),
                        Money.newByYuan(twoStepDaifuItemVo.getTransMoney()).getFen() + "",
                        twoStepDaifuItemVo.getTransNo());
                item.setEntry(twoStepDaifuItemVo);
                item.setCardNo(twoStepDaifuItemVo.getToAccNo());
                item.setChannelBatchid(twoStepDaifuItemVo.getTransBatchid());
                item.setChannelOrderNo(twoStepDaifuItemVo.getTransOrderid());
                list.add(item);
            }
        }
        response.setItemList(list);
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
                BaofuSubmitTwoStepDaifuRequest submitBaofuRequest = this.buildBaofuDaifuSubmitRequest(request,null);
                response = submitBaofuRequest.formatJsonString();
                break;
            case UNION:
                UnionSubmitTwoStepDaifuRequest submitUnionRequest = this.buildUnionDaifuSubmitRequest(request,null);
                response = submitUnionRequest.formatJsonString();
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
        if(request != null){
            List<DaifuSubmitRequest> list = new ArrayList<DaifuSubmitRequest>();
            list.add(request);
            batchRequest = new DaifuSubmitBatchRequest(list);
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
        List<DaifuSubmitRequest> list = Arrays.asList(request);
        DaifuSubmitBatchRequest batchRequest = new DaifuSubmitBatchRequest(list);
        DaifuSubmitBatchResponse response = this.executeUnionDaifuSubmitBatch(batchRequest);
        return response.getItemList().get(0);
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
