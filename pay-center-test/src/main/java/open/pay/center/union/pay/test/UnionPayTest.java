package open.pay.center.union.pay.test;

import open.pay.center.core.pay.way.ProtocolPay;
import open.pay.center.union.pay.UnionPayHandler;
import open.pay.center.union.pay.request.UnionProtocolPayRequest;
import open.pay.center.union.pay.response.UnionProtocolPayResponse;
import org.junit.Test;

/**
 * User: hyman
 * Date: 2017/4/24 0024
 * Time: 10:56
 * Email: qyuhy@qq.com
 */
public class UnionPayTest {
    @Test
    public void testUnionProtocolPay(){
        ProtocolPay payHandler = new UnionPayHandler();
        UnionProtocolPayRequest request = new UnionProtocolPayRequest();
        UnionProtocolPayResponse unionProtocolPayResponse = payHandler.protocolPay(request);
    }

}
