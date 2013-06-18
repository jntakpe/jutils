package com.github.jntakpe.fmk.exception;


/**
 * Exception encapsulant toutes les exceptions métier du framework
 *
 * @author jntakpe
 */
public class BusinessException extends FrameworkException {

    public BusinessException(ErrorCode errorCode, Object... params) {
        super(errorCode, params);
    }

}
