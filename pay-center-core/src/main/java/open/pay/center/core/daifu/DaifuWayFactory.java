package open.pay.center.core.daifu;

import open.pay.center.core.daifu.way.TwoStepDaifu;

/**
 * User: hyman
 * Date: 2017/4/24 0024
 * Time: 14:33
 * Email: qyuhy@qq.com
 */
public interface DaifuWayFactory {
    TwoStepDaifu createTwoStepDaifu();
}
