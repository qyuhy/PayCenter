package open.pay.center.baofu;

import open.pay.center.baofu.service.BaofuMerInfoStoreService;
import open.pay.center.baofu.util.BaofooSecurity;
import open.pay.center.core.AbstractPayCenterFactory;
import open.pay.center.core.daifu.way.TwoStepDaifu;
import open.pay.center.core.model.MerInfo;
import open.pay.center.core.pay.request.PayCoreQueryRequest;
import open.pay.center.core.pay.response.PayCoreQueryResponse;
import open.pay.center.core.pay.way.ProtocolPay;
import open.pay.center.core.pay.way.ScanPay;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * User: hyman
 * Date: 2017/4/24 0024
 * Time: 14:18
 * Email: qyuhy@qq.com
 */
public class AbstractBaoFuPayCenter extends AbstractPayCenterFactory {

    protected TwoStepDaifu injectTwoStepDaifu() {
        return null;
    }

    protected PayCoreQueryResponse doPayQuery(PayCoreQueryRequest request) {
        return null;
    }

    protected ProtocolPay injectProtocolPay() {
        return null;
    }

    protected ScanPay injectScanPay() {
        return null;
    }

    /**
     * 对plain首先进行Base64编码->使用PrivateKey加密->返回16进制的加密字符串
     * @param plain         原文
     * @param privateKey    私钥
     * @return              加密后的文本
     * @throw  PayException 加密异常
     */
    protected String encodeBase64AndEncryptByPrivateKey(String plain, PrivateKey privateKey){
        return BaofooSecurity.encodeBase64AndEncryptByPrivateKey(plain,privateKey);
    }

    /**
     * 解密数据
     * HEX转二进制->私钥解密->Base64解码
     * @param plain
     * @param publicKey
     * @return
     */
    protected String decryptByPublicKeyAndDecodeBase64(String plain, PublicKey publicKey){
        return BaofooSecurity.decryptByPublicKeyAndDecodeBase64(plain,publicKey);
    }

    /**
     * 装载私钥和公钥
     * @param merInfo
     * @return
     */
    protected MerInfo installPrivateKeyAndPublicMerInfo(MerInfo merInfo){
        return BaofuMerInfoStoreService.getInstance().getByMerIdAndTerminalId(merInfo);
    }
}
