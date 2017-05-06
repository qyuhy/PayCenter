package open.pay.center.core.pay.response;

import open.pay.center.core.response.CoreResponse;

/**
 * User: hyman
 * Date: 2017/4/20 0020
 * Time: 17:19
 * Email: qyuhy@qq.com
 */
public class PayCoreQueryResponse extends CoreResponse{

    protected PayCoreQueryResponse(String plainResponse) {
        super(plainResponse);
    }

    @Override
    protected void parsePlainResponse() {
    }
}
