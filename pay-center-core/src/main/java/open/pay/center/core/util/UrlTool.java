package open.pay.center.core.util;

import java.util.HashMap;
import java.util.Map;

/**
 * User: hyman
 * Date: 2017/5/19 0019
 * Time: 14:14
 * Email: qyuhy@qq.com
 */
public class UrlTool {
    /**
     * 将URL参数转换为Map
     * @param param aa=11&bb=22&cc=33
     * @return
     */
    public static Map<String, String> getUrlParams(String param) {
        Map<String, String> map = new HashMap<String, String>();
        if ("".equals(param) || null == param) {
            return map;
        }
        String[] params = param.split("&");
        for (int i = 0; i < params.length; i++) {
            String[] p = params[i].split("=");
            if (p.length == 2) {
                map.put(p[0], p[1]);
            }
        }
        return map;
    }
}
