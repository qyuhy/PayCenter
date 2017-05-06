package open.pay.center.core.request;

import open.pay.center.core.config.Config;
import open.pay.center.core.model.MerInfo;
import open.pay.center.core.util.JsonUtil;

/**
 * User: hyman
 * Date: 2017/4/20 0020
 * Time: 17:09
 * Email: qyuhy@qq.com
 */
public class CoreRequest{
    /**
     * 请求地址
     */
    protected String url;
    /**
     * 商户信息
     */
    protected MerInfo merInfo;
    /**
     * 链接超时时间
     */
    protected int connectionTimeout = Config.DEFAULT_CONNECTION_TIMOUT;
    /**
     * 读取数据超时时间
     */
    protected int readTimeout = Config.DEFAULT_READ_TIMEOUT;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public MerInfo getMerInfo() {
        return merInfo;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setMerInfo(MerInfo merInfo) {
        this.merInfo = merInfo;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    /**
     * 把一个对象转换为JSON字符串
     * @param obj
     * @return
     */
    public String toJsonString(Object obj){
       return JsonUtil.toJson(obj);
    }

    /**
     * 装换为json字符串
     * @return
     */
    public String formatJsonString(){
        return JsonUtil.toJson(this);
    }
}
