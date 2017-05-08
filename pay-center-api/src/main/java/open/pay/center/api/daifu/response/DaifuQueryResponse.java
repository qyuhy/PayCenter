package open.pay.center.api.daifu.response;

import open.pay.center.api.vo.ResponseBaseVo;
import open.pay.center.core.model.ResponseStatus;

/**
 * User: hyman
 * Date: 2017/5/5 0005
 * Time: 18:17
 * Email: qyuhy@qq.com
 */
public class DaifuQueryResponse extends ResponseBaseVo {
    /**
     * 金额：单位分
     */
    private String amount;
    /**
     * 渠道内部订单号
     */
    private String channelOrderNo;
    /**
     * 收款银行账号
     */
    private String cardNo;

    public DaifuQueryResponse(String returnCode, String returnMessage, String returnText, ResponseStatus status, String amount) {
        this.returnCode = returnCode;
        this.returnMessage = returnMessage;
        this.returnText = returnText;
        this.status = status;
        this.amount = amount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getChannelOrderNo() {
        return channelOrderNo;
    }

    public void setChannelOrderNo(String channelOrderNo) {
        this.channelOrderNo = channelOrderNo;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    @Override
    public String toString() {
        return "DaifuSubmitResponse{" +
                "returnCode='" + returnCode + '\'' +
                ", returnMessage='" + returnMessage + '\'' +
                ", returnText='" + returnText + '\'' +
                ", status=" + status +
                ", channelOrderNo='" + channelOrderNo + '\'' +
                ", amount='" + amount + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", entry=" + entry +
                '}';
    }
}
