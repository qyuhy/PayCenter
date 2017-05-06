package open.pay.center.baofu.daifu.request;

import open.pay.center.baofu.daifu.vo.TwoStepDaifuItemVo;
import open.pay.center.baofu.exception.BaofuException;
import open.pay.center.core.daifu.request.SubmitTwoStepDaifuRequest;
import open.pay.center.core.model.MerInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: hyman
 * Date: 2017/4/24 0024
 * Time: 16:09
 * Email: qyuhy@qq.com
 */
public class BaofuSubmitTwoStepDaifuRequest extends SubmitTwoStepDaifuRequest {
    public static final String DATA_TYPE_JSON = "json";
    public static final String DATA_TYPE_XML = "xml";
    public static final String VERSION = "4.0.0";

    /**
     * 数据类型
     */
    private String dataType = DATA_TYPE_JSON;
    /**
     * 请求信息；不能大于5个
     */
    private List<TwoStepDaifuItemVo> items;
    /**
     * 版本号
     */
    private String version = VERSION;
    /**
     * 数据
     */
    private String dataContent;

    public BaofuSubmitTwoStepDaifuRequest(){}
    /**
     * 构造方法
     * @param url           请求地址
     */
    public BaofuSubmitTwoStepDaifuRequest(String url, MerInfo merInfo) {
        this.url = url;
        this.merInfo = merInfo;
    }

    public String getDataContent(){
        if(dataContent == null){
            dataContent = buildDataContent();
        }
        return dataContent;
    }

    /**
     * 实现拼接DataContent字符串
     * @return
     */
    private String buildDataContent(){
        if(items == null || items.isEmpty()){
            throw BaofuException.PARAM_ERROR.newInstance("缺少%s参数","List<SubmitTwoStepDaifuItemVo>");
        }
        //构建最底层trans_reqData item;
        List<Map<String,String>> trans_reqList = new ArrayList<Map<String, String>>();
        for (TwoStepDaifuItemVo item : items) {
            Map<String,String> trans_req = new HashMap<String, String>();
            trans_req.put("trans_no",item.getTransNo());
            trans_req.put("trans_money",item.getTransMoney());
            trans_req.put("to_acc_name",item.getToAccName());
            trans_req.put("to_acc_no",item.getToAccNo());
            trans_req.put("to_bank_name",item.getToBankName());
            trans_req.put("to_pro_name",item.getToProName());
            trans_req.put("to_city_name",item.getToCityName());
            trans_req.put("to_acc_dept",item.getToAccDept());
            trans_req.put("trans_summary",item.getTransSummary());
            trans_reqList.add(trans_req);
        }
        //构建trans_reqData
        Map<String,Object> trans_reqData = new HashMap<String,Object>();
        trans_reqData.put("trans_reqData",trans_reqList);
        List<Map<String,Object>> trans_reqDatas_list = new ArrayList<Map<String, Object>>();
        trans_reqDatas_list.add(trans_reqData);
        //构建trans_reqDatas
        Map<String,Object> trans_reqDatas = new HashMap<String, Object>();
        trans_reqDatas.put("trans_reqDatas",trans_reqDatas_list);
        //构建trans_content
        Map<String,Object> trans_content = new HashMap<String, Object>();
        trans_content.put("trans_content",trans_reqDatas);
        return this.toJsonString(trans_content);
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public List<TwoStepDaifuItemVo> getItems() {
        return items;
    }

    public void setItems(List<TwoStepDaifuItemVo> items) {
        this.items = items;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public static void main(String[] args) {
        BaofuSubmitTwoStepDaifuRequest request = new BaofuSubmitTwoStepDaifuRequest();
        List<TwoStepDaifuItemVo> list = new ArrayList<TwoStepDaifuItemVo>();
        for(int i=1;i<=3;i++){
            list.add(new TwoStepDaifuItemVo("00"+i,"10."+i,"张三"+i,"6626"+i,"招商银行"));
        }
        request.setItems(list);
        System.out.println(request.buildDataContent());

    }
}
