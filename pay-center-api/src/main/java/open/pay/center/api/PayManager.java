package open.pay.center.api;

import open.pay.center.core.pay.way.ProtocolPay;
import open.pay.center.union.pay.UnionPayHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * User: hyman
 * Date: 2017/4/24 0024
 * Time: 11:30
 * Email: qyuhy@qq.com
 */
public class PayManager {
    private static PayManager payManager = new PayManager();
    private static Map<PayChannelEnum,ProtocolPay> protocolPayMap = new HashMap<PayChannelEnum,ProtocolPay>();

    static {
        initProtocolPay();
    }

    private static void initProtocolPay() {
        protocolPayMap.put(PayChannelEnum.UNION,new UnionPayHandler());
    }

    public static PayManager getInstance(){
        return payManager;
    }


    public ProtocolPay getProtocolPay(PayChannelEnum payChannelEnum){
        return payManager.protocolPayMap.get(payChannelEnum);
    }
}
