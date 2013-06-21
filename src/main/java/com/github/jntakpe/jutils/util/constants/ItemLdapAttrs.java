package com.github.jntakpe.jutils.util.constants;

/**
 * Enumération utilisé pour la récupération d'information dans le LDAP sur un poste de travail
 *
 * @author jntakpe
 */
public enum ItemLdapAttrs {

    NOM("cn"),
    DESCRIPTION("description"),
    DATE_CREATION("whenCreated");

    private final String attr;

    private ItemLdapAttrs(String attr) {
        this.attr = attr;
    }

    public String getAttr() {
        return attr;
    }
}
