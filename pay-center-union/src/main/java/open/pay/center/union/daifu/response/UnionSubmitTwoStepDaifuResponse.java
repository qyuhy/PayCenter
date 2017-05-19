package open.pay.center.union.daifu.response;

import open.pay.center.core.daifu.response.SubmitTwoStepDaifuResponse;
import open.pay.center.core.model.ResponseStatus;
import open.pay.center.core.util.UrlTool;
import open.pay.center.union.daifu.vo.UnionDaifuItem;

import java.util.Map;

/**
 * User: hyman
 * Date: 2017/5/18 0018
 * Time: 19:00
 * Email: qyuhy@qq.com
 */
public class UnionSubmitTwoStepDaifuResponse extends SubmitTwoStepDaifuResponse{
    private final String SUCCESS_CODE = "0000";
    public UnionSubmitTwoStepDaifuResponse(String plainResponse) {
        super(plainResponse);
    }

    /**
     * 返回值包装类
     */
    private UnionDaifuItem data;

    @Override
    protected void parsePlainResponse() {
        this.data = new UnionDaifuItem();
        Map<String, String> params = UrlTool.getUrlParams(this.plainResponse);
        this.tipStatus = params.get("responseCode");
        ResponseStatus responseStatus = ResponseStatus.PENDING;
        if(SUCCESS_CODE.equals(this.tipStatus)){
            responseStatus = ResponseStatus.SUCCESS;
            this.tip = "代付请求已接收";
        }else if("0100".equals(this.tipStatus)
                || "0101".equals(this.tipStatus)
                || "0102".equals(this.tipStatus)
                || "0103".equals(this.tipStatus)
                || "0104".equals(this.tipStatus)){
            //失败情况
            responseStatus = ResponseStatus.ERROR;
            this.tip = "代付请求受理失败";
        }else{
            this.tip = "代付请求受理中";
        }
        this.status = responseStatus;
        //交易状态
        String stat = params.get("stat");
        ResponseStatus statStatus = ResponseStatus.PENDING;
        if(stat != null ){
            /**
             * s	成功	交易成功	状态码为小写字母s
             2	处理中	交易已接受
             3	处理中	财务已确认
             4	处理中	财务处理中
             5	处理中	已发往银行	ChinaPay已将代付交易发往银行。后续若银行返回结果，该状态会相应更新。
             6	失败	银行已退单	银行退单，交易失败。
             7	处理中	重汇已提交
             8	处理中	重汇已发送	ChinaPay已将代付交易发往银行。后续若银行返回结果，该状态会相应更新。
             9	失败	重汇已退单	银行对重汇的代付交易退单，交易失败。
             */
            if("s".equals(stat)){
                statStatus = ResponseStatus.SUCCESS;
            }else if("6".equals(stat) || "9".equals(stat)){
                statStatus = ResponseStatus.ERROR;
            }
        }
        if(params.get("merSeqId") == null && params.get("merDate") == null){
            statStatus = ResponseStatus.ERROR;
        }
        this.data.setTransState(statStatus);
        this.data.setMerId(params.get("merId"));
        this.data.setMerDate(params.get("merDate"));
        this.data.setMerSeqId(params.get("merSeqId"));
        this.data.setCpDate(params.get("cpDate"));
        this.data.setCpSeqId(params.get("cpSeqId"));
        this.data.setTransAmt(params.get("transAmt"));
        this.data.setStat(params.get("stat"));
        this.data.setCardNo(params.get("cardNo"));
        this.data.setChkValue(params.get("chkValue"));
    }


    public UnionDaifuItem getData() {
        return data;
    }

    @Override
    public String toString() {
        return "UnionSubmitTwoStepDaifuResponse{" +
                "data=" + data +
                ",tip=" + this.tip +
                ",tipStatus=" + this.tipStatus +
                ",status="+this.status +
                ",plainResponse=" + plainResponse +
                '}';
    }
}
