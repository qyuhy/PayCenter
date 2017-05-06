package open.pay.center.core.util;

import java.util.UUID;

/**
 * User: hyman
 * Date: 2017/5/5 0005
 * Time: 11:05
 * Email: qyuhy@qq.com
 */
public class StringUtil {
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }


    public static Long parseDoubleToLongString(String doubleParam) {
        if (doubleParam.contains(".")) {
            return Long.parseLong(doubleParam.substring(0, doubleParam.indexOf(".")));
        }
        return Long.parseLong(doubleParam);
    }
}