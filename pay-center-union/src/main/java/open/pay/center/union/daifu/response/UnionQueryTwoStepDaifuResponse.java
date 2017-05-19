package open.pay.center.union.daifu.response;

import open.pay.center.core.daifu.response.QueryTwoStepDaifuResponse;
import open.pay.center.core.model.ResponseStatus;
import open.pay.center.union.daifu.vo.UnionDaifuItem;

/**
 * User: hyman
 * Date: 2017/5/18 0018
 * Time: 19:03
 * Email: qyuhy@qq.com
 */
public class UnionQueryTwoStepDaifuResponse extends QueryTwoStepDaifuResponse {
    private final String SUCCESS_CODE = "000";
    /**
     * 返回值包装类
     */
    private UnionDaifuItem data;

    public UnionQueryTwoStepDaifuResponse(String plainResponse) {
        super(plainResponse);
    }

    @Override
    protected void parsePlainResponse() {
        this.data = new UnionDaifuItem();
        String[] dataArray = this.plainResponse.split("\\|");
        String code = dataArray[0];
        String merId = dataArray[1];
        String merDate = dataArray[2];
        String merSeqId = dataArray[3];
        String cpDate = dataArray[4];
        String cpSeqId = dataArray[5];
        String bankName = dataArray[6];
        String cardNo = dataArray[7];
        String usrName = dataArray[8];
        String transAmt = dataArray[9];
        String feeAmt = dataArray[10];
        String prov = dataArray[11];
        String city = dataArray[12];
        String purpose = dataArray[13];
        String stat = dataArray[14];
        String backDate = dataArray[15];
        String chkValue = dataArray[16];
        this.tipStatus = code;
        ResponseStatus responseStatus = ResponseStatus.PENDING;
        if(SUCCESS_CODE.equals(this.tipStatus)){
            responseStatus = ResponseStatus.SUCCESS;
            this.tip = "代付查询请求已接收";
        }else if("001".equals(this.tipStatus)
                || "002".equals(this.tipStatus)
                ){
            //失败情况
            responseStatus = ResponseStatus.ERROR;
            this.tip = "代付查询请求受理失败";
        }else{
            this.tip = "代付查询请求求受理中";
        }
        this.status = responseStatus;
        //交易状态
        ResponseStatus statStatus = ResponseStatus.PENDING;
        if(stat != null && stat.length() >= 0){
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
        if((merSeqId == null || merSeqId.length()<=0) && (merDate == null && merDate.length()<=0)){
            statStatus = ResponseStatus.ERROR;
        }
        this.data.setTransState(statStatus);
        this.data.setMerId(merId);
        this.data.setMerDate(merDate);
        this.data.setMerSeqId(merSeqId);
        this.data.setCpDate(cpDate);
        this.data.setCpSeqId(cpSeqId);
        this.data.setBankName(bankName);
        this.data.setCardNo(cardNo);
        this.data.setUsrName(usrName);
        this.data.setTransAmt(transAmt);
        this.data.setFeeAmt(feeAmt);
        this.data.setProv(prov);
        this.data.setCity(city);
        this.data.setPurpose(purpose);
        this.data.setStat(stat);
        this.data.setBackDate(backDate);
        this.data.setChkValue(chkValue);
    }

    public UnionDaifuItem getData() {
        return data;
    }

    @Override
    public String toString() {
        return "UnionQueryTwoStepDaifuResponse{" +
                "data=" + data +
                ",tip=" + this.tip +
                ",tipStatus=" + this.tipStatus +
                ",status="+this.status +
                ",plainResponse=" + plainResponse +
                '}';
    }
}
