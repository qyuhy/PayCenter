package open.pay.center.baofu;

import open.pay.center.core.AbstractPayCenterFactory;
import open.pay.center.core.daifu.way.TwoStepDaifu;
import open.pay.center.core.pay.request.PayCoreQueryRequest;
import open.pay.center.core.pay.response.PayCoreQueryResponse;
import open.pay.center.core.pay.way.ProtocolPay;
import open.pay.center.core.pay.way.ScanPay;

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
}
