package open.pay.center.api.daifu.request;

import java.util.List;

/**
 * User: hyman
 * Date: 2017/5/11 0011
 * Time: 17:01
 * Email: qyuhy@qq.com
 */
public class DaifuSubmitBatchRequest {
    /**
     * 请求列表
     */
    private List<DaifuSubmitRequest> itemList;

    public DaifuSubmitBatchRequest(List<DaifuSubmitRequest> itemList) {
        this.itemList = itemList;
    }

    public List<DaifuSubmitRequest> getItemList() {
        return itemList;
    }

    public void setItemList(List<DaifuSubmitRequest> itemList) {
        this.itemList = itemList;
    }
}
