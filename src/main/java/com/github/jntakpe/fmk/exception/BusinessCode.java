package com.github.jntakpe.fmk.exception;

/**
 * Enumération des exceptions métiers
 *
 * @author jntakpe
 */
public enum BusinessCode implements ErrorCode {

    REPOSITORY_METHOD_MISSING,
    LDAP_USER_ATTR_MISSING,
    LDAP_ITEM_ATTR_MISSING,
    LDAP_USER_MISSING,
    LDAP_NO_SINGLE_RESULT,
    FUTURE_BIRTHDATE,
    EMAIL_MISSING_PARAM

}
