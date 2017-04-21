package open.pay.center.core.pay.query;

import open.pay.center.core.pay.request.PayCoreQueryRequest;
import open.pay.center.core.pay.response.PayCoreQueryResponse;

/**
 * User: hyman
 * Date: 2017/4/20 0020
 * Time: 17:17
 * Email: qyuhy@qq.com
 */
public interface PayQuery {
    PayCoreQueryResponse query(PayCoreQueryRequest request);
}
