package open.pay.center.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.ValueFilter;

/**
 * User: hyman
 * Date: 2017/4/13 0013
 * Time: 16:23
 * Email: qyuhy@qq.com
 */
public class JsonUtil {
    private static ValueFilter filter = new ValueFilter() {
        @Override
        public Object process(Object obj, String s, Object v) {
            if (v == null)
                return "";
            return v;
        }
    };
    /**
     * 把对象转换为JSON字符串
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String toJson(T obj) {
        return JSONObject.toJSONString(obj,filter);
    }

    /**
     * 把字符串转换为JSON对象
     * @param text
     * @return
     */
    public static JSONObject parseObject(String text){
        return JSON.parseObject(text);
    }
}
