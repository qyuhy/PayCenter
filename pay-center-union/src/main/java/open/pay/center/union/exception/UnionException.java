package open.pay.center.union.exception;

import open.pay.center.core.exception.PayException;

/**
 * User: hyman
 * Date: 2017/5/4 0004
 * Time: 10:13
 * Email: qyuhy@qq.com
 */
public class UnionException extends PayException{
    public static final UnionException PARAM_ERROR = new UnionException(
            203001,"参数异常"
    );
    public static final UnionException ENCODE_ERROR = new UnionException(
            204001,"编码异常"
    );
    public static final UnionException SIGN_ERROR = new UnionException(
            205001,"签名异常"
    );



    public UnionException(int code, String msgFormat, Object... args) {
        super(String.format(msgFormat, args));
        this.code = code;
        this.msg = String.format(msgFormat, args);
    }

    public UnionException() {
        super();
    }

    public UnionException(String message) {
        super(message);
    }

    public UnionException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnionException(Throwable cause) {
        super(cause);
    }
}
