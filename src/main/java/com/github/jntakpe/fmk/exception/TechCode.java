package com.github.jntakpe.fmk.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.OptimisticLockingFailureException;

/**
 * Codes d'erreurs relatifs aux exceptions techniques
 *
 * @author jntakpe
 */
public enum TechCode implements ErrorCode {

    CONSTRAINT_VIOLATION(DataIntegrityViolationException.class,
            "Échec de l'opération suite à une violation de contrainte d'intégrité."),
    OPTIMISTIC_LOCKING(OptimisticLockingFailureException.class,
            "Échec de l'opération suite à la détection d'une mise à jour concurrente."),
    NO_RESULT(EmptyResultDataAccessException.class,
            "Échec de l'opération, cette entité est introuvable.");

    private final Class<?> sourceException;

    private final String message;

    private TechCode(Class<?> sourceException, String message) {
        this.sourceException = sourceException;
        this.message = message;
    }

    public Class<?> getSourceException() {
        return sourceException;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
