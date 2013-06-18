package com.github.jntakpe.fmk.exception;

/**
 * Exceptions gérées par le framework. Super type des business et tech exceptions.
 *
 * @author jntakpe
 */
public abstract class FrameworkException extends RuntimeException {

    protected ErrorCode errorCode;

    protected Object[] params;

    protected FrameworkException(ErrorCode errorCode, Object[] params) {
        this.errorCode = errorCode;
        this.params = params;
    }

    public FrameworkException(String message, ErrorCode errorCode, Object... params) {
        super(message);
        this.errorCode = errorCode;
        this.params = params;
    }


    public FrameworkException(Throwable cause, ErrorCode errorCode, Object... params) {
        super(cause);
        this.errorCode = errorCode;
        this.params = params;
    }


    public FrameworkException(String message, Throwable cause, ErrorCode errorCode, Object... params) {
        super(message, cause);
        this.errorCode = errorCode;
        this.params = params;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }
}
