package open.pay.center.core.pay.way;

import open.pay.center.core.pay.request.PayCoreRequest;
import open.pay.center.core.pay.response.PayCoreResponse;

/**
 * User: hyman
 * Date: 2017/4/20 0020
 * Time: 14:40
 * Email: qyuhy@qq.com
 * 协议支付接口
 */
public interface ProtocolPay {
    <T extends PayCoreResponse> T protocolPay(PayCoreRequest request);
}
