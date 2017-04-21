package open.pay.center.union.pay;

import open.pay.center.core.pay.request.PayCoreRequest;
import open.pay.center.core.pay.response.PayCoreResponse;
import open.pay.center.core.pay.way.ProtocolPay;
import open.pay.center.union.AbstractUnionPayCenter;

/**
 * User: hyman
 * Date: 2017/4/20 0020
 * Time: 18:09
 * Email: qyuhy@qq.com
 */
public class UnionPayHandler extends AbstractUnionPayCenter implements ProtocolPay{
    public PayCoreResponse protocolPay(PayCoreRequest request) {
        return null;
    }

    @Override
    protected ProtocolPay injectProtocolPay() {
        return this;
    }
}
