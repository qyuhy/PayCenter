package open.pay.center.core.util.cer;

import open.pay.center.core.exception.PayException;

import java.security.*;

/**
 * User: hyman
 * Date: 2017/5/4 0004
 * Time: 15:36
 * Email: qyuhy@qq.com
 */
public class KeyGen {

    /**
     *  生成密钥对
     * @param algorithm 指定算法名称
     * @param keysize 密钥长度
     * @param random 随机源
     * @return
     */
    public KeyPair getKeyPair(String algorithm, int keysize, SecureRandom random){
        KeyPairGenerator keyGen = null;
        KeyPair keyPair = null;
        try {
        keyGen = KeyPairGenerator.getInstance(algorithm);
        keyGen.initialize(keysize,random);
        keyPair = keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new PayException("生成KeyPair异常",e);
        }
        return keyPair;
    }

    /**
     * 获取公钥
     * @param keyPair 密钥对
     * @return
     */
    public PublicKey getPublicKey(KeyPair keyPair){
        return (PublicKey)keyPair.getPublic();
    }
    /**
     * 获取私钥
     * @param keyPair 密钥对
     * @return
     */
    public PrivateKey getPrivateKey(KeyPair keyPair){
        return (PrivateKey)keyPair.getPrivate();
    }

}
