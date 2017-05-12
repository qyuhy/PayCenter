package open.pay.center.api.daifu.response;

import open.pay.center.api.vo.ResponseBaseVo;
import open.pay.center.core.model.ResponseStatus;

/**
 * User: hyman
 * Date: 2017/5/5 0005
 * Time: 18:00
 * Email: qyuhy@qq.com
 */
public class DaifuSubmitResponse extends ResponseBaseVo{
    /**
     * 商户订单号
     */
    private String orderNo;
    /**
     * 渠道内部订单号
     */
    private String channelOrderNo;
    /**
     * 金额：单位分
     */
    private String amount;
    /**
     * 同请求报文参数中的收款账号
     */
    private String cardNo;
    /**
     * 批次号
     */
    private String channelBatchid;


    public DaifuSubmitResponse(String returnCode, String returnMessage, String returnText, ResponseStatus status,String amount,String orderNo) {
        this.returnCode = returnCode;
        this.returnMessage = returnMessage;
        this.returnText = returnText;
        this.status = status;
        this.amount = amount;
        this.orderNo = orderNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getChannelOrderNo() {
        return channelOrderNo;
    }

    public void setChannelOrderNo(String channelOrderNo) {
        this.channelOrderNo = channelOrderNo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getChannelBatchid() {
        return channelBatchid;
    }

    public void setChannelBatchid(String channelBatchid) {
        this.channelBatchid = channelBatchid;
    }


    @Override
    public String toString() {
        return "DaifuSubmitResponse{" +
                "returnCode='" + returnCode + '\'' +
                ", returnMessage='" + returnMessage + '\'' +
                ", returnText='" + returnText + '\'' +
                ", status=" + status +
                ", orderNo='" + orderNo + '\'' +
                ", channelOrderNo='" + channelOrderNo + '\'' +
                ", amount='" + amount + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", channelBatchid='" + channelBatchid + '\'' +
                ", supportSynchronized=" + this.isSupportSynchronized() +
                ", transStatus=" + this.getTransStatus() +
                ", entry=" + entry +
                '}';
    }


}
