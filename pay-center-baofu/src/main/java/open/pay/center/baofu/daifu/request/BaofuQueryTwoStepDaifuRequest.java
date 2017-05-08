package open.pay.center.baofu.daifu.request;

import open.pay.center.baofu.daifu.vo.TwoStepDaifuItemVo;
import open.pay.center.core.daifu.request.QueryTwoStepDaifuRequest;
import open.pay.center.core.model.MerInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: hyman
 * Date: 2017/5/5 0005
 * Time: 14:31
 * Email: qyuhy@qq.com
 */
public class BaofuQueryTwoStepDaifuRequest extends QueryTwoStepDaifuRequest{
    public static final String DATA_TYPE_JSON = "json";
    public static final String DATA_TYPE_XML = "xml";
    public static final String VERSION = "4.0.0";
    /**
     * 请求参数
     */
    private List<TwoStepDaifuItemVo> params;
    /**
     * 请求数据
     */
    private String dataContent;
    /**
     * 数据类型
     */
    private String dataType = DATA_TYPE_JSON;
    /**
     * 版本号
     */
    private String version = VERSION;

    public BaofuQueryTwoStepDaifuRequest(){};

    public BaofuQueryTwoStepDaifuRequest(String url, MerInfo merInfo,List<TwoStepDaifuItemVo> params) {
        this.params = params;
        this.url = url;
        this.merInfo = merInfo;
    }

    public List<TwoStepDaifuItemVo> getParams() {
        return params;
    }

    public void setParams(List<TwoStepDaifuItemVo> params) {
        this.params = params;
    }

    public String getDataContent() {
        if(dataContent == null){
            this.dataContent = this.buildDataContent();
        }
        return dataContent;
    }

    private String buildDataContent() {
        //构建最底层trans_reqData item;
        List<Map<String,String>> trans_reqList = new ArrayList<Map<String, String>>();
        for (TwoStepDaifuItemVo item : params) {
            Map<String,String> trans_req = new HashMap<String, String>();
            trans_req.put("trans_no",item.getTransNo());
            trans_req.put("trans_batchid",item.getTransMoney());
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

    public void setDataContent(String dataContent) {
        this.dataContent = dataContent;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
