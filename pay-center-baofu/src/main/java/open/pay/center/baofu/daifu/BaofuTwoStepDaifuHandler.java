package open.pay.center.baofu.daifu;

import open.pay.center.baofu.AbstractBaoFuPayCenter;
import open.pay.center.core.daifu.request.DaifuCoreRequest;
import open.pay.center.core.daifu.response.DaifuCoreResponse;
import open.pay.center.core.daifu.way.TwoStepDaifu;

/**
 * User: hyman
 * Date: 2017/4/24 0024
 * Time: 15:39
 * Email: qyuhy@qq.com
 */
public class BaofuTwoStepDaifuHandler extends AbstractBaoFuPayCenter implements TwoStepDaifu{

    @Override
    protected TwoStepDaifu injectTwoStepDaifu() {
        return this;
    }

    public <T extends DaifuCoreResponse> T submitTwoStepDaifu(DaifuCoreRequest request) {
        return null;
    }

    public <T extends DaifuCoreResponse> T queryTwoStepDaifu(DaifuCoreRequest request) {
        return null;
    }
}
