package open.pay.center.baofu.service;

import open.pay.center.core.model.MerInfo;
import open.pay.center.core.util.KeyTool;

import java.util.HashMap;
import java.util.Map;

/**
 * User: hyman
 * Date: 2017/5/6 0006
 * Time: 10:36
 * Email: qyuhy@qq.com
 */
public class BaofuMerInfoStoreService {
    private static BaofuMerInfoStoreService INSTANCE = new BaofuMerInfoStoreService();
    private Map<String,MerInfo> cache = new HashMap<String, MerInfo>();

    /**
     * 获取已经加载过PublicKey和PrivateKey的商户信息
     * @param merInfo
     * @return
     */
    public MerInfo getByMerIdAndTerminalId(MerInfo merInfo){
        String key = merInfo.getMerId() + merInfo.getTerminalId();
        if(cache.containsKey(key)){
            return cache.get(key);
        }
        merInfo.setPublicKey(KeyTool.loadByCerPath(merInfo.getPublicKeyPath()));
        merInfo.setPrivateKey(KeyTool.loadPrivateKeyByPfxPath(merInfo.getPrivateKeyPath(),merInfo.getPublicKeyPassword()));
        cache.put(key,merInfo);
        return merInfo;
    }

    private BaofuMerInfoStoreService() {
    }

    public static BaofuMerInfoStoreService getInstance(){
        return INSTANCE;
    }

}
