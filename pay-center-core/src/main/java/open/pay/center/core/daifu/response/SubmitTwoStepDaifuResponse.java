package open.pay.center.core.daifu.response;

/**
 * User: hyman
 * Date: 2017/5/3 0003
 * Time: 14:13
 * Email: qyuhy@qq.com
 */
public class SubmitTwoStepDaifuResponse extends DaifuCoreResponse{

    protected SubmitTwoStepDaifuResponse(String plainResponse) {
        super(plainResponse);
    }

    @Override
    protected void parsePlainResponse() {
    }
}
