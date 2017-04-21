package open.pay.center.core.pay;

import open.pay.center.core.pay.way.ProtocolPay;
import open.pay.center.core.pay.way.ScanPay;

/**
 * User: hyman
 * Date: 2017/4/20 0020
 * Time: 14:34
 * Email: qyuhy@qq.com
 */
public interface PayWayFactory {
    ProtocolPay createProtocolPay();
    ScanPay createScanPay();
}
