package com.github.jntakpe.fmk.util.constant;

/**
 * Enumération des attributs LDAP utilisé par le framework
 *
 * @author jntakpe
 */
public enum LdapAttrs {

    LOGIN("samaccountname"),
    NOM("name"),
    MAIL("mail"),
    MATRICULE("employeeid"),
    TELEPHONE("telephonenumber");

    private final String attr;

    private LdapAttrs(String attr) {
        this.attr = attr;
    }

    public String getAttr() {
        return attr;
    }
}
