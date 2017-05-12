package open.pay.center.api.daifu.response;

import open.pay.center.api.vo.ResponseBaseVo;
import open.pay.center.core.model.ResponseStatus;

import java.util.List;

/**
 * User: hyman
 * Date: 2017/5/11 0011
 * Time: 17:47
 * Email: qyuhy@qq.com
 */
public class DaifuSubmitBatchResponse extends ResponseBaseVo{
    private List<DaifuSubmitResponse> itemList;

    /**
     *
     * @param returnCode
     * @param returnMessage
     * @param returnText
     * @param status
     */
    public DaifuSubmitBatchResponse(String returnCode, String returnMessage, String returnText, ResponseStatus status) {
        this.returnCode = returnCode;
        this.returnMessage = returnMessage;
        this.returnText = returnText;
        this.status = status;
    }

    public List<DaifuSubmitResponse> getItemList() {
        return itemList;
    }

    public void setItemList(List<DaifuSubmitResponse> itemList) {
        this.itemList = itemList;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        if(itemList != null && itemList.size()>0){
            itemList.stream().forEach(item -> builder.append(item.toString()));
        }
        return "DaifuSubmitResponse{" +
                "returnCode='" + returnCode + '\'' +
                ", returnMessage='" + returnMessage + '\'' +
                ", returnText='" + returnText + '\'' +
                ", status=" + status +
                ", supportSynchronized=" + this.isSupportSynchronized() +
                ", transStatus=" + this.getTransStatus() +
                ", itemList=" + builder +
                ", entry=" + entry +
                '}';
    }

}
