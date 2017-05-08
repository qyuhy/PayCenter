package open.pay.center.api.daifu.request;

/**
 * User: hyman
 * Date: 2017/5/5 0005
 * Time: 18:16
 * Email: qyuhy@qq.com
 */
public class DaifuQueryRequest {
    /**
     * 商户订单号
     */
    private String orderNo;
    /**
     * 请求批次号
     */
    private String batchId;
    /**
     * 商户提交日期
     */
    private String date;


    public DaifuQueryRequest batchId(String batchId){
        this.batchId = batchId;
        return this;
    }

    public DaifuQueryRequest date(String date){
        this.date = date;
        return this;
    }

    private DaifuQueryRequest(String orderNo) {
        this.orderNo = orderNo;
    }

    public static DaifuQueryRequest build(String orderNo){
        DaifuQueryRequest daifuQueryRequest = new DaifuQueryRequest(orderNo);
        return daifuQueryRequest;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public String getBatchId() {
        return batchId;
    }

    public String getDate() {
        return date;
    }
}
