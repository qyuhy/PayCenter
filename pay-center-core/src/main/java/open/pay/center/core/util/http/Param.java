package open.pay.center.core.util.http;

import java.util.HashMap;
import java.util.Map;

/**
 * User: hyman
 * Date: 2017/5/5 0005
 * Time: 10:14
 * Email: qyuhy@qq.com
 * Http参数方便客户端实用类
 */
public class Param {
    private Map<String,String> data = new HashMap<String,String>();

    public Param of(String key,String value){
        data.put(key,value);
        return this;
    }

    public static Param build(){
        return new Param();
    }

    public Map<String,String> getData(){
        return data;
    }
}
