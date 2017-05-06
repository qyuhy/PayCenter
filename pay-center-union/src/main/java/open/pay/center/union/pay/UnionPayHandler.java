package open.pay.center.union.pay;

import open.pay.center.core.pay.request.PayCoreRequest;
import open.pay.center.core.pay.response.PayCoreResponse;
import open.pay.center.core.pay.way.ProtocolPay;
import open.pay.center.union.AbstractUnionPayCenter;
import open.pay.center.union.pay.response.UnionProtocolPayResponse;

/**
 * User: hyman
 * Date: 2017/4/20 0020
 * Time: 18:09
 * Email: qyuhy@qq.com
 */
public class UnionPayHandler extends AbstractUnionPayCenter implements ProtocolPay{

    public <T extends PayCoreResponse> T protocolPay(PayCoreRequest request) {
        UnionProtocolPayResponse response = new UnionProtocolPayResponse(null);
        System.out.println("底层执行了........");
        return cast(response);
    }

    @Override
    protected ProtocolPay injectProtocolPay() {
        return this;
    }

    public static <T> T cast(Object t) {
        T object =(T)t;
        return object;
    }
}
