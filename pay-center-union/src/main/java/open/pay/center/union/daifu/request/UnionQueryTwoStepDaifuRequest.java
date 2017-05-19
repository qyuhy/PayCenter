package open.pay.center.union.daifu.request;

import open.pay.center.core.daifu.request.QueryTwoStepDaifuRequest;
import open.pay.center.core.model.MerInfo;

/**
 * User: hyman
 * Date: 2017/5/18 0018
 * Time: 19:02
 * Email: qyuhy@qq.com
 */
public class UnionQueryTwoStepDaifuRequest extends QueryTwoStepDaifuRequest {
    public static final String VERSION = "20090501";
    public static final String SIGN_FLAG = "1";
    /**
     * 在收付捷平台中开通的商户编号
     */
    private String merId;
    /**
     * 标志该笔交易发生的日期,格式为YYYYMMDD。仅允许查询最近一个月的交易订单
     */
    private String merDate;
    /**
     * 商户号+商户流水号+商户日期唯一标示一笔交易订单
     */
    private String merSeqId;
    /**
     * 版本号
     */
    private String version = VERSION;
    /**
     * 签名标志
     */
    private String signFlag = SIGN_FLAG;
    /**
     * 签名值
     */
    private String chkValue;

    /**
     * 签名值明文
     */
    private String plainSignData;

    /**
     * 构建签名明文
     * @return
     */
    private String buildSignPlain(){
        /**
         * merId + merDate +merSeqId +version
         */
        StringBuilder sb = new StringBuilder();
        sb.append(merId)
                .append(merDate)
                .append(merSeqId)
                .append(version);
        return sb.toString();
    }

    public String getPlainSignData() {
        if(plainSignData == null){
            this.plainSignData = buildSignPlain();
        }
        return plainSignData;
    }

    public UnionQueryTwoStepDaifuRequest(String url, String merId, MerInfo merInfo, String merDate, String merSeqId) {
        this.url = url;
        this.merId = merId;
        this.merInfo = merInfo;
        this.merDate = merDate;
        this.merSeqId = merSeqId;
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

    public String getChkValue() {
        return chkValue;
    }

    public void setChkValue(String chkValue) {
        this.chkValue = chkValue;
    }
}
