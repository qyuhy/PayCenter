package open.pay.center.core.util;

import java.util.Base64;

/**
 * User: hyman
 * Date: 2017/5/4 0004
 * Time: 20:29
 * Email: qyuhy@qq.com
 */
public class Base64Util {
    /**
     * <p>
     * BASE64字符串解码为二进制数据
     * </p>
     *
     * @param base64 base64
     * @return 源二进制数据
     */
    public static byte[] decode(String base64) {
        return Base64.getDecoder().decode(base64.getBytes());
    }

    /**
     * BASE64字符串解码为二进制数据
     * @param plain
     * @return
     */
    public static byte[] decode(byte [] plain){
        return Base64.getDecoder().decode(plain);
    }

    /**
     * <p>
     * 二进制数据编码为BASE64字符串
     * </p>
     *
     * @param bytes base64
     * @return BASE64后的二进制数据
     */
    public static String encode(byte[] bytes) {
        return new String(Base64.getEncoder().encode(bytes));
    }
}
