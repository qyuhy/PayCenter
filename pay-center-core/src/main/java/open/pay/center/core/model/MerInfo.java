package open.pay.center.core.model;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * User: hyman
 * Date: 2017/5/6 0006
 * Time: 10:18
 * Email: qyuhy@qq.com
 */
public class MerInfo {
    /**
     * 商户号
     */
    private String merId;
    /**
     * 终端号
     */
    private String terminalId;
    /**
     * 私钥地址
     */
    private String privateKeyPath;
    /**
     * 公钥地址
     */
    private String publicKeyPath;
    /**
     * 私钥密码，针对pfx文件
     */
    private String publicKeyPassword;
    /**
     * 私钥
     */
    private PrivateKey privateKey;
    /**
     * 公钥
     */
    private PublicKey publicKey;
    /**
     * 非标准私钥
     */
    private Object priKey;
    /**
     * 非标准公钥
     */
    private Object pubKey;

    public MerInfo(){};

    public MerInfo(String merId, String terminalId, String privateKeyPath, String publicKeyPath, String publicKeyPassword) {
        this.merId = merId;
        this.terminalId = terminalId;
        this.privateKeyPath = privateKeyPath;
        this.publicKeyPath = publicKeyPath;
        this.publicKeyPassword = publicKeyPassword;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getPrivateKeyPath() {
        return privateKeyPath;
    }

    public void setPrivateKeyPath(String privateKeyPath) {
        this.privateKeyPath = privateKeyPath;
    }

    public String getPublicKeyPath() {
        return publicKeyPath;
    }

    public void setPublicKeyPath(String publicKeyPath) {
        this.publicKeyPath = publicKeyPath;
    }

    public String getPublicKeyPassword() {
        return publicKeyPassword;
    }

    public void setPublicKeyPassword(String publicKeyPassword) {
        this.publicKeyPassword = publicKeyPassword;
    }

    public Object getPriKey() {
        return priKey;
    }

    public void setPriKey(Object priKey) {
        this.priKey = priKey;
    }

    public Object getPubKey() {
        return pubKey;
    }

    public void setPubKey(Object pubKey) {
        this.pubKey = pubKey;
    }
}
