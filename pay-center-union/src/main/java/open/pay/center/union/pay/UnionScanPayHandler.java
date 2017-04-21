package open.pay.center.union.pay;

import open.pay.center.core.pay.request.PayCoreRequest;
import open.pay.center.core.pay.request.PrepareScanPayRequest;
import open.pay.center.core.pay.request.ScanPayRequest;
import open.pay.center.core.pay.response.PayCoreResponse;
import open.pay.center.core.pay.response.PrepareScanPayResponse;
import open.pay.center.core.pay.response.ScanPayResponse;
import open.pay.center.core.pay.way.ScanPay;
import open.pay.center.union.AbstractUnionPayCenter;

/**
 * User: hyman
 * Date: 2017/4/20 0020
 * Time: 18:09
 * Email: qyuhy@qq.com
 */
public class UnionScanPayHandler extends AbstractUnionPayCenter implements ScanPay{
    public PayCoreResponse protocolPay(PayCoreRequest request) {
        return null;
    }

    @Override
    protected ScanPay injectScanPay() {
        return this;
    }

    public PrepareScanPayResponse prepareScanPay(PrepareScanPayRequest request) {
        return null;
    }

    public ScanPayResponse scanPay(ScanPayRequest request) {
        return null;
    }
}
