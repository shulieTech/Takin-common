package io.shulie.takin.parent.exception.entity;

import io.shulie.takin.parent.exception.utils.ExceptionUtils;

/**
 * @author shiyajian
 * create: 2020-09-25
 */
public class BaseException extends RuntimeException {

    private ExceptionReadable ex;

    private Object source;



    public BaseException(ExceptionReadable ex, Object source) {
        super(ExceptionUtils.getCallStackMessage(ex,source));
        this.ex = ex;
        this.source = source;

    }

    /**
     * 通过使用异常链，我们可以提高代码的可理解性、系统的可维护性和友好性。
     * @param ex
     * @param e
     */
    public BaseException(ExceptionReadable ex, Throwable e) {
        super(ExceptionUtils.getCallStackMessage(ex,null),e);
        this.ex = ex;
    }

    /**
     * 通过使用异常链，我们可以提高代码的可理解性、系统的可维护性和友好性。
     * @param ex
     * @param source
     * @param e
     */
    public BaseException(ExceptionReadable ex, Object source , Throwable e) {
        super(ExceptionUtils.getCallStackMessage(ex,source),e);
        this.ex = ex;
        this.source = source;
    }



    public ExceptionReadable getEx() {
        return ex;
    }

    public void setEx(ExceptionReadable ex) {
        this.ex = ex;
    }

    public Object getSource() {
        return source;
    }

    public void setSource(Object source) {
        this.source = source;
    }

    //@Override
    //public synchronized Throwable fillInStackTrace() {
    //    return this;
    //}
}
