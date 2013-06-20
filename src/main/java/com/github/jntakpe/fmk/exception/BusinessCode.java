package com.github.jntakpe.fmk.exception;

/**
 * Enumération des exceptions métiers
 *
 * @author jntakpe
 */
public enum BusinessCode implements ErrorCode {

    DEFAULT_EXCEPTION("Une erreur inconnue est survenue."),
    EMAIL_MISSING_PARAM("Le paramètre {} est obligatoire pour la configuration SMTP."),
    LDAP_MISSING_ATTR("Impossible de récupérer l'attribut {} pour l'utilisateur {}");

    private final String message;

    private BusinessCode(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
