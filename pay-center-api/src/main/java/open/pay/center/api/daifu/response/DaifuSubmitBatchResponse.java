package open.pay.center.api.daifu.response;

import open.pay.center.api.vo.ResponseBaseVo;

import java.util.List;

/**
 * User: hyman
 * Date: 2017/5/11 0011
 * Time: 17:47
 * Email: qyuhy@qq.com
 */
public class DaifuSubmitBatchResponse extends ResponseBaseVo{
    private List<DaifuSubmitResponse> itemList;

    public List<DaifuSubmitResponse> getItemList() {
        return itemList;
    }

    public void setItemList(List<DaifuSubmitResponse> itemList) {
        this.itemList = itemList;
    }
}
