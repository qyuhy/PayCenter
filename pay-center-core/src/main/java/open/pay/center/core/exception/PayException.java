package open.pay.center.core.exception;

/**
 * User: hyman
 * Date: 2017/4/12 0012
 * Time: 18:22
 * Email: qyuhy@qq.com
 */
public class PayException extends RuntimeException{

    public static final PayException UN_IMPL_METHOD = new PayException(
      200001,"未实现的方法"
    );
    public static final PayException PRI_FILE_NOT_EXSITS = new PayException(
            205001,"私钥文件不存在"
    );
    public static final PayException PUB_FILE_NOT_EXSITS = new PayException(
            205002,"公钥文件不存在"
    );


    /**
     * 异常信息
     */
    protected String msg;
    /**
     * 异常编码
     * 统一格式：A-BB-CCC
     * A:错误级别，如1代表系统级错误，2代表服务级错误；
     * B:项目或模块名称，一般公司不会超过99个项目；
     * C:具体错误编号，自增即可，一个项目999种错误应该够用
     */
    protected int code;


    public PayException(int code, String msgFormat, Object... args) {
        super(String.format(msgFormat, args));
        this.code = code;
        this.msg = String.format(msgFormat, args);
    }

    public PayException() {
        super();
    }

    public PayException(String message) {
        super(message);
    }

    public PayException(String message, Throwable cause) {
        super(message, cause);
    }

    public PayException(Throwable cause) {
        super(cause);
    }

    /**
     * 生成一个新实例
     * @param msgFormat
     * @param args
     * @return
     */
    public PayException newInstance(String msgFormat, Object... args) {
        return new PayException(this.code, msgFormat, args);
    }

}
