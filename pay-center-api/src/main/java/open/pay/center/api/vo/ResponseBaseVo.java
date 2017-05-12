package open.pay.center.api.vo;

import open.pay.center.core.model.ResponseStatus;

/**
 * User: hyman
 * Date: 2017/5/6 0006
 * Time: 18:07
 * Email: qyuhy@qq.com
 */
public class ResponseBaseVo {
    /**
     * 返回值代码
     */
    protected String returnCode;
    /**
     * 返回值提示信息
     */
    protected String returnMessage;
    /**
     * 返回值原始信息;
     * 方便客户端保存原始的返回的报文信息；
     */
    protected String returnText;
    /**
     * 接口调用状态
     */
    protected ResponseStatus status;
    /**
     * 实体对象;
     */
    protected Object entry;
    /**
     * 接口是否支持同步返回状态
     */
    private boolean supportSynchronized = false;
    /**
     * 交易状态
     */
    private ResponseStatus transStatus;


    public boolean isSupportSynchronized() {
        return supportSynchronized;
    }

    public void setSupportSynchronized(boolean supportSynchronized) {
        this.supportSynchronized = supportSynchronized;
    }

    public ResponseStatus getTransStatus() {
        return transStatus;
    }

    public void setTransStatus(ResponseStatus transStatus) {
        this.transStatus = transStatus;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    public String getReturnText() {
        return returnText;
    }

    public void setReturnText(String returnText) {
        this.returnText = returnText;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public Object getEntry() {
        return entry;
    }

    public void setEntry(Object entry) {
        this.entry = entry;
    }
}
