package open.pay.center.core.daifu.way;

import open.pay.center.core.daifu.request.QueryTwoStepDaifuRequest;
import open.pay.center.core.daifu.request.SubmitTwoStepDaifuRequest;
import open.pay.center.core.daifu.response.QueryTwoStepDaifuResponse;
import open.pay.center.core.daifu.response.SubmitTwoStepDaifuResponse;

/**
 * User: hyman
 * Date: 2017/4/24 0024
 * Time: 14:39
 * Email: qyuhy@qq.com
 * 两阶段提交
 */
public interface TwoStepDaifu {
    /**
     * 提交代付请求
     * @param request
     * @param <T>
     * @return
     */
    <T extends SubmitTwoStepDaifuResponse> T submitTwoStepDaifu(SubmitTwoStepDaifuRequest request);

    /**
     * 查询代付请求结果
     * @param request
     * @param <T>
     * @return
     */
    <T extends QueryTwoStepDaifuResponse> T queryTwoStepDaifu(QueryTwoStepDaifuRequest request);
}
