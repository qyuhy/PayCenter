package open.pay.center.core;

import open.pay.center.core.pay.PayWayFactory;
import open.pay.center.core.pay.query.PayQuery;

/**
 * User: hyman
 * Date: 2017/4/20 0020
 * Time: 14:43
 * Email: qyuhy@qq.com
 */
public interface PayCenterFactory {
    PayWayFactory createPayWayFactory();
    PayQuery createPayQuery();
}
