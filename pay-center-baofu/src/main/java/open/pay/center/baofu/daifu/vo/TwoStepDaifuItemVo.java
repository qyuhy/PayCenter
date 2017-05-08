package open.pay.center.baofu.daifu.vo;

import open.pay.center.core.model.ResponseStatus;

/**
 * User: hyman
 * Date: 2017/5/4 0004
 * Time: 10:10
 * Email: qyuhy@qq.com
 */
public class TwoStepDaifuItemVo {
    /**
     * 商户订单号
     */
    private String transNo;
    /**
     * 转账金额
     */
    private String transMoney;
    /**
     * 收款人姓名
     */
    private String toAccName;
    /**
     * 收款人银行账号
     */
    private String toAccNo;
    /**
     * 收款人银行名称
     */
    private String toBankName;
    /**
     * 收款人开户行省名
     */
    private String toProName;
    /**
     * 收款人开户行市名
     */
    private String toCityName;
    /**
     * 收款人开户行机构名
     */
    private String toAccDept;
    /**
     * 摘要
     */
    private String transSummary;

    //========response========
    /**
     * 宝付订单号
     */
    private String transOrderid;
    /**
     * 宝付批次号
     */
    private String transBatchid;
    /**
     * 交易手续费
     */
    private String transFee;
    /**
     * 订单交易处理状态
     */
    private String state;
    /**
     * 备注（错误信息）
     */
    private String transRemark;
    /**
     * 交易申请时间
     */
    private String transStarttime;
    /**
     * 交易完成时间
     */
    private String transEnndtime;
    /**
     * 收款方宝付会员号
     */
    private String toMemberId;
    /**
     * 订单交易状态
     */
    private ResponseStatus transState;

    /**
     * 构造函数
     * @param transNo       商户订单号
     * @param transMoney    交易金额，单位元
     * @param toAccName     收款人姓名
     * @param toAccNo       收款人银行帐号
     * @param toBankName    收款人开户行机构名
     */
    public TwoStepDaifuItemVo(String transNo, String transMoney, String toAccName, String toAccNo, String toBankName) {
        this.transNo = transNo;
        this.transMoney = transMoney;
        this.toAccName = toAccName;
        this.toAccNo = toAccNo;
        this.toBankName = toBankName;
    }

    public TwoStepDaifuItemVo(String transNo, String transBatchid){
        this.transNo = transNo;
        this.transBatchid = transBatchid;
    }

    /**
     * 构建查询信息
     * @param transNo
     * @param transBatchid
     * @return
     */
    public static TwoStepDaifuItemVo newQueryParam(String transNo, String transBatchid){
        return new TwoStepDaifuItemVo(transNo,transBatchid);
    }

    public ResponseStatus getTransState() {
        return transState;
    }

    public void setTransState(ResponseStatus transState) {
        this.transState = transState;
    }

    public String getTransFee() {
        return transFee;
    }

    public void setTransFee(String transFee) {
        this.transFee = transFee;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTransRemark() {
        return transRemark;
    }

    public void setTransRemark(String transRemark) {
        this.transRemark = transRemark;
    }

    public String getTransStarttime() {
        return transStarttime;
    }

    public void setTransStarttime(String transStarttime) {
        this.transStarttime = transStarttime;
    }

    public String getTransEnndtime() {
        return transEnndtime;
    }

    public void setTransEnndtime(String transEnndtime) {
        this.transEnndtime = transEnndtime;
    }

    public String getToMemberId() {
        return toMemberId;
    }

    public void setToMemberId(String toMemberId) {
        this.toMemberId = toMemberId;
    }

    public String getTransOrderid() {
        return transOrderid;
    }

    public void setTransOrderid(String transOrderid) {
        this.transOrderid = transOrderid;
    }

    public String getTransBatchid() {
        return transBatchid;
    }

    public void setTransBatchid(String transBatchid) {
        this.transBatchid = transBatchid;
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public String getTransMoney() {
        return transMoney;
    }

    public void setTransMoney(String transMoney) {
        this.transMoney = transMoney;
    }

    public String getToAccName() {
        return toAccName;
    }

    public void setToAccName(String toAccName) {
        this.toAccName = toAccName;
    }

    public String getToAccNo() {
        return toAccNo;
    }

    public void setToAccNo(String toAccNo) {
        this.toAccNo = toAccNo;
    }

    public String getToBankName() {
        return toBankName;
    }

    public void setToBankName(String toBankName) {
        this.toBankName = toBankName;
    }

    public String getToProName() {
        return toProName;
    }

    public void setToProName(String toProName) {
        this.toProName = toProName;
    }

    public String getToCityName() {
        return toCityName;
    }

    public void setToCityName(String toCityName) {
        this.toCityName = toCityName;
    }

    public String getToAccDept() {
        return toAccDept;
    }

    public void setToAccDept(String toAccDept) {
        this.toAccDept = toAccDept;
    }

    public String getTransSummary() {
        return transSummary;
    }

    public void setTransSummary(String transSummary) {
        this.transSummary = transSummary;
    }
}
