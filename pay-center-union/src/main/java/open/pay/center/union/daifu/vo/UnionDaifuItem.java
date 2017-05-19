package open.pay.center.union.daifu.vo;

import open.pay.center.core.model.ResponseStatus;

/**
 * User: hyman
 * Date: 2017/5/19 0019
 * Time: 14:52
 * Email: qyuhy@qq.com
 */
public class UnionDaifuItem {
    /**
     *同请求报文参数中商户号
     */
    private String merId;
    /**
     * 原始订单交易日期
     */
    private String merDate;
    /**
     * 交易记录流水号，同商户日期，商户号一起唯一标识报文中的一笔交易(merId + merDate + merSeqId唯一确定一条记录)
     */
    private String merSeqId;
    /**
     * ChinaPay接收到交易的日期
     */
    private String cpDate;
    /**
     * ChinaPay系统内部流水
     */
    private String cpSeqId;
    /**
     * 原始订单参数中的开户银行
     */
    private String bankName;
    /**
     * 原始订单参数中的收款账号，仅显示后5位
     */
    private String cardNo;
    /**
     * 原始订单参数中收款人姓名
     */
    private String usrName;
    /**
     * 原始订单参数中的金额
     */
    private String transAmt;
    /**
     * 整数，以分为单位
     */
    private String feeAmt;
    /**
     * 原始订单参数中的省份
     */
    private String prov;
    /**
     * 原始订单参数中的城市
     */
    private String city;
    /**
     * 原始订单参数中的用途
     */
    private String purpose;
    /**
     * 交易状态码
     */
    private String stat;
    /**
     * 银行退单日期
     */
    private String backDate;
    /**
     * 除了报文尾部的256位以上的都是作为签名的明文。明文需进行BASE64编码，具体见下方。
     */
    private String chkValue;
    /**
     * 订单交易状态
     */
    private ResponseStatus transState;

    public ResponseStatus getTransState() {
        return transState;
    }

    public void setTransState(ResponseStatus transState) {
        this.transState = transState;
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId;
    }

    public String getMerDate() {
        return merDate;
    }

    public void setMerDate(String merDate) {
        this.merDate = merDate;
    }

    public String getMerSeqId() {
        return merSeqId;
    }

    public void setMerSeqId(String merSeqId) {
        this.merSeqId = merSeqId;
    }

    public String getCpDate() {
        return cpDate;
    }

    public void setCpDate(String cpDate) {
        this.cpDate = cpDate;
    }

    public String getCpSeqId() {
        return cpSeqId;
    }

    public void setCpSeqId(String cpSeqId) {
        this.cpSeqId = cpSeqId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getUsrName() {
        return usrName;
    }

    public void setUsrName(String usrName) {
        this.usrName = usrName;
    }

    public String getTransAmt() {
        return transAmt;
    }

    public void setTransAmt(String transAmt) {
        this.transAmt = transAmt;
    }

    public String getFeeAmt() {
        return feeAmt;
    }

    public void setFeeAmt(String feeAmt) {
        this.feeAmt = feeAmt;
    }

    public String getProv() {
        return prov;
    }

    public void setProv(String prov) {
        this.prov = prov;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public String getBackDate() {
        return backDate;
    }

    public void setBackDate(String backDate) {
        this.backDate = backDate;
    }

    public String getChkValue() {
        return chkValue;
    }

    public void setChkValue(String chkValue) {
        this.chkValue = chkValue;
    }

    @Override
    public String toString() {
        return "UnionDaifuItem{" +
                "merId='" + merId + '\'' +
                ", merDate='" + merDate + '\'' +
                ", merSeqId='" + merSeqId + '\'' +
                ", transState='" + transState + '\'' +
                ", cpDate='" + cpDate + '\'' +
                ", cpSeqId='" + cpSeqId + '\'' +
                ", bankName='" + bankName + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", usrName='" + usrName + '\'' +
                ", transAmt='" + transAmt + '\'' +
                ", feeAmt='" + feeAmt + '\'' +
                ", prov='" + prov + '\'' +
                ", city='" + city + '\'' +
                ", purpose='" + purpose + '\'' +
                ", stat='" + stat + '\'' +
                ", backDate='" + backDate + '\'' +
                ", chkValue='" + chkValue + '\'' +
                '}';
    }
}
