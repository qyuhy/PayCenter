package open.pay.center.union.service;

import chinapay.PrivateKey;
import open.pay.center.core.model.MerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * User: hyman
 * Date: 2017/5/6 0006
 * Time: 10:36
 * Email: qyuhy@qq.com
 */
public class UnionMerInfoStoreService {
    private Logger logger = LoggerFactory.getLogger(UnionMerInfoStoreService.class);
    private static UnionMerInfoStoreService INSTANCE = new UnionMerInfoStoreService();
    private Map<String,MerInfo> cache = new HashMap<String, MerInfo>();

    /**
     * 获取已经加载过PublicKey和PrivateKey的商户信息
     * @param merInfo
     * @return
     */
    public MerInfo getByMerIdAndTerminalId(MerInfo merInfo){
        String key = merInfo.getMerId();
        if(cache.containsKey(key)){
            return cache.get(key);
        }
        PrivateKey privateKey = new PrivateKey();
        PrivateKey publicKey = new PrivateKey();
        try {
            //构建私钥
            boolean priBuildOk = false;
            logger.info("china pay build privatekey start merId[{}], privatePath[{}]", merInfo.getMerId(), merInfo.getPrivateKeyPath());
            //通过商户订单号获取私钥地址。
            priBuildOk = privateKey.buildKey(merInfo.getMerId(), 0, merInfo.getPrivateKeyPath());
            if(!priBuildOk){
                logger.error("china pay build privatekey fail");
                return null;
            }
            //构建公钥
            boolean pubBuildOk = false;
            logger.info("china pay build publickey merId[{}], publicPath[{}]",  merInfo.getMerId(), merInfo.getPublicKeyPath());
            pubBuildOk = publicKey.buildKey("999999999999999", 0,  merInfo.getPublicKeyPath());
            if(!pubBuildOk){
                logger.error("china pay build publicKey fail");
                return null;
            }
        } catch (Exception e) {
            logger.error("china pay build privatekey or public key exception->"+e.getMessage(),e);
            e.printStackTrace();
        }
        merInfo.setPriKey(privateKey);
        merInfo.setPubKey(publicKey);
        cache.put(key,merInfo);
        return merInfo;
    }

    private UnionMerInfoStoreService() {
    }

    public static UnionMerInfoStoreService getInstance(){
        return INSTANCE;
    }

}
