package com.github.jntakpe.fmk.exception;

/**
 * Enumération des exceptions métiers
 *
 * @author jntakpe
 */
public enum BusinessCode implements ErrorCode {

    LDAP_USER_ATTR_MISSING,
    LDAP_ITEM_ATTR_MISSING,
    LDAP_USER_MISSING,
    LDAP_NO_SINGLE_RESULT

}
