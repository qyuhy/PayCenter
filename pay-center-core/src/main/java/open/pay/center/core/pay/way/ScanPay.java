package open.pay.center.core.pay.way;

import open.pay.center.core.pay.request.PrepareScanPayRequest;
import open.pay.center.core.pay.request.ScanPayRequest;
import open.pay.center.core.pay.response.PrepareScanPayResponse;
import open.pay.center.core.pay.response.ScanPayResponse;

/**
 * User: hyman
 * Date: 2017/4/21 0021
 * Time: 10:10
 * Email: qyuhy@qq.com
 * 扫码支付
 */
public interface ScanPay {
    PrepareScanPayResponse prepareScanPay(PrepareScanPayRequest request);
    ScanPayResponse scanPay(ScanPayRequest request);
}
