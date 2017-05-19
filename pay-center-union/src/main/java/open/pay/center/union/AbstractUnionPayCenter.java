package open.pay.center.union;

import open.pay.center.core.AbstractPayCenterFactory;
import open.pay.center.core.daifu.way.TwoStepDaifu;
import open.pay.center.core.model.MerInfo;
import open.pay.center.core.pay.request.PayCoreQueryRequest;
import open.pay.center.core.pay.response.PayCoreQueryResponse;
import open.pay.center.core.pay.way.ProtocolPay;
import open.pay.center.core.pay.way.ScanPay;
import open.pay.center.union.service.UnionMerInfoStoreService;

/**
 * User: hyman
 * Date: 2017/4/20 0020
 * Time: 14:53
 * Email: qyuhy@qq.com
 * 抽象银联基类
 * 可以在此类中添加银联公用的签名类
 */
public class AbstractUnionPayCenter extends AbstractPayCenterFactory{

    protected TwoStepDaifu injectTwoStepDaifu() {
        return null;
    }

    /**
     * 子类覆盖此方法
     * @param request
     * @return
     */
    protected PayCoreQueryResponse doPayQuery(PayCoreQueryRequest request) {
        return null;
    }

    protected ProtocolPay injectProtocolPay() {
        return null;
    }

    protected ScanPay injectScanPay() {
        return null;
    }


    public MerInfo installPrivateKeyAndPublicMerInfo(MerInfo merInfo) {
        return UnionMerInfoStoreService.getInstance().getByMerIdAndTerminalId(merInfo);
    }
}
