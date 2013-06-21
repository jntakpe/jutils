package com.github.jntakpe.fmk.exception;

/**
 * Enumération des exceptions métiers
 *
 * @author jntakpe
 */
public enum BusinessCode implements ErrorCode {

    DEFAULT_EXCEPTION("Une erreur inconnue est survenue."),
    EMAIL_MISSING_PARAM("Le paramètre {} est obligatoire pour la configuration SMTP."),
    LDAP_USER_MISSING("Impossible de récupérer l'attribut {} pour l'utilisateur {}"),
    LDAP_ITEM_MISSING("Impossible de récupérer l'attribut {} pour l'item {}"),
    LDAP_NO_SINGLE_RESULT("La recherche du nom d'utilisateur {} n'a pas renvoyé de résultat unique");

    private final String message;

    private BusinessCode(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
