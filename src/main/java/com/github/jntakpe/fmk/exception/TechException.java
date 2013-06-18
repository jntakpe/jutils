package com.github.jntakpe.fmk.exception;

/**
 * Exception encapsulant les exceptions techniques (accès DB ou accès fichier ...)
 *
 * @author jntakpe
 */
public class TechException extends FrameworkException {

    public TechException(String message, ErrorCode errorCode, Object... params) {
        super(message, errorCode, params);
    }

    public TechException(Throwable cause, ErrorCode errorCode, Object... params) {
        super(cause, errorCode, params);
    }

    public TechException(String message, Throwable cause, ErrorCode errorCode, Object... params) {
        super(message, cause, errorCode, params);
    }
}
