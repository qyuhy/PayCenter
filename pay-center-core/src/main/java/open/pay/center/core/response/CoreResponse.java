package open.pay.center.core.response;

import open.pay.center.core.model.ResponseStatus;

import java.io.Serializable;
import java.util.HashMap;

/**
 * User: hyman
 * Date: 2017/4/20 0020
 * Time: 17:09
 * Email: qyuhy@qq.com
 */
public class CoreResponse extends HashMap implements Serializable{
    /**
     *  返回值状态
     */
    protected ResponseStatus status;
    /**
     *  原始数据状态
     */
    protected String tipStatus;
    /**
     * 消息提示信息
     */
    protected String tip;


    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public String getTipStatus() {
        return tipStatus;
    }

    public void setTipStatus(String tipStatus) {
        this.tipStatus = tipStatus;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
}
