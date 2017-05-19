package open.pay.center.api.daifu.request;

/**
 * User: hyman
 * Date: 2017/5/5 0005
 * Time: 17:58
 * Email: qyuhy@qq.com
 */
public class DaifuSubmitRequest {
    /**
     * 商户订单号
     * M
     */
    private String orderNo;
    /**
     * 交易金额
     * M
     */
    private String amount;
    /**
     * 收款人姓名
     * M
     */
    private String userName;
    /**
     * 收款账号:银行卡号或者存折号
     * M
     */
    private String cardNo;
    /**
     *M收款人开户行所在省
     */
    private String province;
    /**
     *M收款人开户行所在地区
     */
    private String city;
    /**
     * 收款人银行名称
     */
    private String bankName;
    /**
     *  开户支行名称。
     */
    private String subBank;
    /**
     * 备注
     */
    private String remark;


    public DaifuSubmitRequest subBank(String subBank){
        this.subBank = subBank;
        return this;
    }

    public DaifuSubmitRequest bankName(String bankName){
        this.bankName = bankName;
        return this;
    }

    public String getSubBank() {
        return subBank;
    }

    public String getRemark() {
        return remark;
    }

    /**
     *
     * @param orderNo
     * @param amount
     * @param userName
     * @param cardNo
     * @param province
     * @param city
     * @param remark
     */
    private DaifuSubmitRequest(String orderNo, String amount, String userName, String cardNo, String province, String city,String remark) {
        this.orderNo = orderNo;
        this.amount = amount;
        this.userName = userName;
        this.cardNo = cardNo;
        this.province = province;
        this.city = city;
        this.remark = remark;
    }

    public static DaifuSubmitRequest build(String orderNo, String amount, String userName, String cardNo, String province, String city,String remark){
        return new DaifuSubmitRequest(orderNo,amount,userName,cardNo,province,city,remark);
    }


    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
