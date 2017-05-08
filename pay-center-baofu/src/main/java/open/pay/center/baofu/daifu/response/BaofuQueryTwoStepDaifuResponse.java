package open.pay.center.baofu.daifu.response;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import open.pay.center.baofu.daifu.vo.TwoStepDaifuItemVo;
import open.pay.center.core.daifu.response.QueryTwoStepDaifuResponse;
import open.pay.center.core.model.ResponseStatus;
import open.pay.center.core.util.JsonUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * User: hyman
 * Date: 2017/4/24 0024
 * Time: 16:11
 * Email: qyuhy@qq.com
 */
public class BaofuQueryTwoStepDaifuResponse extends QueryTwoStepDaifuResponse {
    /**
     * 返回值信息
     */
    private Map<String,TwoStepDaifuItemVo> items;

    public BaofuQueryTwoStepDaifuResponse(String plainResponse) {
        super(plainResponse);
        //this.parsePlainResponse();
    }

    @Override
    protected void parsePlainResponse() {
        //解析返回值数据
        super.parsePlainResponse();
        JSONObject jsonObject = JsonUtil.parseObject(this.plainResponse);
        JSONObject trans_content = jsonObject.getJSONObject("trans_content");
        JSONObject trans_head = trans_content.getJSONObject("trans_head");
        String return_code = trans_head.getString("return_code");
        String return_msg = trans_head.getString("return_msg");
        this.setTipStatus(return_code);
        this.setTip(return_msg);
        boolean success = false;
        if("0000".equals(return_code)){
            this.status = ResponseStatus.SUCCESS;
            success = true;
        }else if("0300".equals(return_code) || "0999".equals(return_code)){
            this.status = ResponseStatus.PENDING;
        }else{
            this.status = ResponseStatus.ERROR;
        }
        if(success == false){
            return;
        }
        JSONArray array = new JSONArray();
        JSONArray trans_reqDatasArray = trans_content.getJSONArray("trans_reqDatas");
        for(int i=0; i<trans_reqDatasArray.size(); i++){
            JSONObject temp = trans_reqDatasArray.getJSONObject(i);
            Object temp2 = temp.get("trans_reqData");
            if(temp2 != null){
                if(temp2 instanceof JSONObject){
                    array.add(temp2);
                }else if(temp2 instanceof JSONArray){
                    array = (JSONArray)temp2;
                }
            }
        }
        if(array == null || array.size() == 0){
            return;
        }
        this.items = new HashMap(array.size());
        for(int i=0; i<array.size(); i++){
            JSONObject obj = array.getJSONObject(i);
            String trans_orderid = obj.getString("trans_orderid");
            String trans_batchid = obj.getString("trans_batchid");
            String trans_no = obj.getString("trans_no");
            String trans_money = obj.getString("trans_money");
            String to_acc_name = obj.getString("to_acc_name");
            String to_acc_no = obj.getString("to_acc_no");
            String to_acc_dept = obj.getString("to_acc_dept");
            String trans_summary = obj.getString("trans_summary");
            String trans_fee = obj.getString("trans_fee");
            String state = obj.getString("state");
            String trans_remark = obj.getString("trans_remark");
            String trans_starttime = obj.getString("trans_starttime");
            String trans_endtime = obj.getString("trans_endtime");
            TwoStepDaifuItemVo item = new TwoStepDaifuItemVo(trans_no,trans_money,to_acc_name,to_acc_no,null);
            item.setTransOrderid(trans_orderid);
            item.setTransBatchid(trans_batchid);
            item.setToAccDept(to_acc_dept);
            item.setTransSummary(trans_summary);
            item.setTransFee(trans_fee);
            item.setTransRemark(trans_remark);
            item.setState(state);
            item.setTransStarttime(trans_starttime);
            item.setTransEnndtime(trans_endtime);
            items.put(trans_no,item);
        }
    }

    @Override
    public String toString() {
        return "BaofuQueryTwoStepDaifuResponse{" +
                "items=" + JsonUtil.toJson(this.items)+
                ",tip=" + this.tip +
                ",tipStatus=" + this.tipStatus +
                ",status="+this.status +
                ",plainResponse=" + plainResponse +
                "}";
    }

    public Map<String, TwoStepDaifuItemVo> getItems() {
        return items;
    }
}
