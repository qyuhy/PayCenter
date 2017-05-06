package open.pay.center.baofu.exception;

import open.pay.center.core.exception.PayException;

/**
 * User: hyman
 * Date: 2017/5/4 0004
 * Time: 10:13
 * Email: qyuhy@qq.com
 */
public class BaofuException extends PayException{
    public static final PayException PARAM_ERROR = new BaofuException(
            201001,"参数异常"
    );


    public BaofuException(int code, String msgFormat, Object... args) {
        super(String.format(msgFormat, args));
        this.code = code;
        this.msg = String.format(msgFormat, args);
    }

    public BaofuException() {
        super();
    }

    public BaofuException(String message) {
        super(message);
    }

    public BaofuException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaofuException(Throwable cause) {
        super(cause);
    }
}
