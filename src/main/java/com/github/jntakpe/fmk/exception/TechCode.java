package com.github.jntakpe.fmk.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.OptimisticLockingFailureException;

import javax.mail.MessagingException;
import java.io.IOException;

/**
 * Codes d'erreurs relatifs aux exceptions techniques
 *
 * @author jntakpe
 */
public enum TechCode implements ErrorCode {

    CONSTRAINT_VIOLATION(DataIntegrityViolationException.class),
    OPTIMISTIC_LOCKING(OptimisticLockingFailureException.class),
    NO_RESULT(EmptyResultDataAccessException.class),
    IO(IOException.class),
    MESSAGING(MessagingException.class);

    private final Class<?> sourceException;

    private TechCode(Class<?> sourceException) {
        this.sourceException = sourceException;
    }

    public Class<?> getSourceException() {
        return sourceException;
    }
}
