package open.pay.center.baofu.util;

import open.pay.center.baofu.exception.BaofuException;
import open.pay.center.core.util.Base64Util;
import open.pay.center.core.util.HexUtil;
import open.pay.center.core.util.RsaUtil;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * User: hyman
 * Date: 2017/5/4 0004
 * Time: 20:28
 * Email: qyuhy@qq.com
 */
public class BaofooSecurity {

    /**
     * 对plain首先进行Base64编码->使用PrivateKey加密->返回16进制的加密字符串
     * @param plain         原文
     * @param privateKey    私钥
     * @return              加密后的文本
     * @throw  PayException 加密异常
     */
    public static String encodeBase64AndEncryptByPrivateKey(String plain, PrivateKey privateKey){
        String encode = Base64Util.encode(plain.getBytes());
        String encryptDataContent = null;
        try {
            encryptDataContent = HexUtil.byte2Hex(RsaUtil.encryptByPrivateKey(encode.getBytes(),privateKey));
        } catch (Exception e) {
            throw new BaofuException("宝付加密异常",e);
        }
        return encryptDataContent;
    }

    /**
     * 解密数据
     * HEX转二进制->私钥解密->Base64解码
     * @param plain
     * @param publicKey
     * @return
     */
    public static String decryptByPublicKeyAndDecodeBase64(String plain, PublicKey publicKey){
        String retValue = null;
        try {
            byte[] bytes = RsaUtil.decryptByPublicKey(HexUtil.hex2Bytes(plain), publicKey);
            retValue = new String(Base64Util.decode(bytes));
        } catch (Exception e) {
            throw new BaofuException("宝付解密异常",e);
        }
        return retValue;
    }

}
