package open.pay.center.union.daifu.request;

import open.pay.center.core.daifu.request.SubmitTwoStepDaifuRequest;
import open.pay.center.core.model.MerInfo;

/**
 * User: hyman
 * Date: 2017/5/18 0018
 * Time: 18:58
 * Email: qyuhy@qq.com
 */
public class UnionSubmitTwoStepDaifuRequest extends SubmitTwoStepDaifuRequest {
    public static final String VERSION = "20160530";
    public static final String SIGN_FLAG = "1";
    public static final String FLAG_PUBLIC = "01";
    public static final String FLAG_PRIVATE = "00";

    /**
     * 商户号
     */
    private String merId;
    /**
     * 商户日期
     * YYYYMMDD
     */
    private String merDate;
    /**
     * 商户流水号
     * 变长，16位,数字
     */
    private String merSeqId;
    /**
     * 收款账号
     * 银行卡号或者存折号
     */
    private String cardNo;
    /**
     * 收款人在银行开户时留存的开户姓名
     */
    private String usrName;
    /**
     * 开户银行名称
     */
    private String openBank;
    /**
     * 收款人开户行所在省
     */
    private String prov;
    /**
     * 收款人开户行所在地区
     */
    private String city;
    /**
     * 整数，货币种类为人民币，以分为单位
     */
    private String transAmt;
    /**
     * 存款用途
     */
    private String purpose;
    /**
     * 付款标志
     * 对公对私标记。“00”对私，“01”对公。该字段可以不填，如不填则默认为对私。
     */
    private String flag = "00";
    /**
     * 版本号
     * 固定为“20160530”
     */
    private String version = VERSION;
    /**
     * 签名标志
     * 固定为“1”
     */
    private String signFlag = SIGN_FLAG;
    /**
     *渠道类型
     *表示商户代付业务使用场景，（业务参数）
     *07：互联网
     *08：移动端
     */
    private String termType = "07";
    /**
     * 交易模式
     * 表示商户代付业务交易模式
     *（业务参数）
     *0：被动发起代付
     *1：主动发起代付
     *
     */
    private String payMode = "1";
    /**
     * 签名值
     * 签名字段，具体见下方
     */
    private String chkValue;

    private String plainSignData;

    private String buildSignPlain(){
        /**
         * merId + merDate +merSeqId +cardNo + usrName+certType+certId+ openBank +prov +city + transAmt + purpose +
         * subBank + flag + version + termType+payMode+userId+userRegisterTime+userMail+userMobile+diskSn+mac+imei+
         * ip+coordinates+baseStationSn+codeInputType+mobileForBank+desc
         */
        StringBuilder sb = new StringBuilder();
        sb.append(merId)
                .append(merDate)
                .append(merSeqId)
                .append(cardNo)
                .append(usrName)
                .append(openBank)
                .append(prov)
                .append(city)
                .append(transAmt)
                .append(purpose)
                .append(flag)
                .append(version)
                .append(termType)
                .append(payMode);
        return sb.toString();
    }

    public String getPlainSignData() {
        if(this.plainSignData == null){
            this.plainSignData = this.buildSignPlain();
        }
        return plainSignData;
    }

    public UnionSubmitTwoStepDaifuRequest(){};

    public UnionSubmitTwoStepDaifuRequest(String merId, MerInfo merInfo, String url) {
        this.merId = merId;
        this.merInfo = merInfo;
        this.url = url;
    }

    /**
     *
     * @param merId     商户号
     * @param merInfo   商户信息
     * @param url       地址
     * @param merDate   日期
     * @param merSeqId  序列号
     * @param cardNo    收款账号
     * @param usrName   收款人在银行开户时留存的开户姓名
     * @param openBank  开户银行名称
     * @param prov      省份
     * @param city      城市
     * @param transAmt  金额，单位分
     * @param purpose   备注
     */
    public UnionSubmitTwoStepDaifuRequest(String merId,MerInfo merInfo,String url, String merDate, String merSeqId,
                                          String cardNo, String usrName, String openBank, String prov, String city, String transAmt, String purpose) {
        this.merId = merId;
        this.merDate = merDate;
        this.merSeqId = merSeqId;
        this.cardNo = cardNo;
        this.usrName = usrName;
        this.openBank = openBank;
        this.prov = prov;
        this.city = city;
        this.transAmt = transAmt;
        this.purpose = purpose;
        this.merInfo = merInfo;
        this.url = url;
    }

    /**
     * 构造函数
     * @param merDate   日期
     * @param merSeqId  序列号
     * @param cardNo    收款账号
     * @param usrName   收款人在银行开户时留存的开户姓名
     * @param openBank  开户银行名称
     * @param prov      省份
     * @param city      城市
     * @param transAmt  金额，单位分
     * @param purpose   备注
     */
    public UnionSubmitTwoStepDaifuRequest(String merSeqId, String merDate,
                                          String cardNo, String usrName, String openBank, String prov, String city, String transAmt, String purpose) {
        this.merDate = merDate;
        this.merSeqId = merSeqId;
        this.cardNo = cardNo;
        this.usrName = usrName;
        this.openBank = openBank;
        this.prov = prov;
        this.city = city;
        this.transAmt = transAmt;
        this.purpose = purpose;
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

    public String getOpenBank() {
        return openBank;
    }

    public void setOpenBank(String openBank) {
        this.openBank = openBank;
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

    public String getTransAmt() {
        return transAmt;
    }

    public void setTransAmt(String transAmt) {
        this.transAmt = transAmt;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSignFlag() {
        return signFlag;
    }

    public void setSignFlag(String signFlag) {
        this.signFlag = signFlag;
    }

    public String getTermType() {
        return termType;
    }

    public void setTermType(String termType) {
        this.termType = termType;
    }

    public String getPayMode() {
        return payMode;
    }

    public void setPayMode(String payMode) {
        this.payMode = payMode;
    }

    public String getChkValue() {
        return chkValue;
    }

    public void setChkValue(String chkValue) {
        this.chkValue = chkValue;
    }
}
