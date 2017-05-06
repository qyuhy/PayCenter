package open.pay.center.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.io.ByteArrayOutputStream;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * User: hyman
 * Date: 2017/5/4 0004
 * Time: 19:54
 * Email: qyuhy@qq.com
 */
public class RsaUtil {
    private Logger logger = LoggerFactory.getLogger(RsaUtil.class);
    /**
     * 密钥位数
     */
    public static final int RAS_KEY_SIZE = 1024;
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 128 - 11;
    /**
     * 填充方式
     */
    public static final String KEY_ALGORITHM_PADDING = "RSA/ECB/PKCS1Padding";

    /**
     * 使用私钥加密数据
     * @param data          待加密数据
     * @param privateKey    私钥
     * @return              加密后的数据
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, PrivateKey privateKey) throws Exception{
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM_PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return doFinal(cipher, data, MAX_ENCRYPT_BLOCK);
    }

    /**
     * 使用公钥解密
     * @param data
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM_PADDING);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return doFinal(cipher, data, MAX_DECRYPT_BLOCK);
    }


    /**
     * 注意：RSA加密明文最大长度117字节，
     * 解密要求密文最大长度为128字节，
     * 所以在加密和解密的过程中需要分块进行。
     *
     * @param cipher 密钥
     * @param data   待处理的数据
     * @return 处理后的值
     * @throws BadPaddingException！
     * @throws IllegalBlockSizeException
     */
    private static byte[] doFinal(Cipher cipher, byte[] data, int key_len) throws BadPaddingException, IllegalBlockSizeException {
        int inputLen = data.length, offset = 0;
        byte[] tmp;
        ByteArrayOutputStream out = new ByteArrayOutputStream(getTmpArrayLength(inputLen));
        while (inputLen > 0) {
            tmp = cipher.doFinal(data, offset, Math.min(key_len, inputLen));
            out.write(tmp, 0, tmp.length);
            offset += key_len;
            inputLen -= key_len;
        }
        return out.toByteArray();
    }

    private static int getTmpArrayLength(int L) {
        int S = MAX_DECRYPT_BLOCK;
        while (S < L) S <<= 1;
        return S;
    }
}
