package com.github.jntakpe.jutils.util.constants;

/**
 * Enumération des attributs LDAP utilisés pour la récupération d'information sur un utilisateur
 *
 * @author jntakpe
 */
public enum UtilisateurLdapAttrs {

    LOGIN("samaccountname"),
    NOM("cn"),
    MAIL("mail"),
    MATRICULE("employeeid"),
    TELEPHONE("telephonenumber"),
    AGENCE("l");

    private final String attr;

    private UtilisateurLdapAttrs(String attr) {
        this.attr = attr;
    }

    public String getAttr() {
        return attr;
    }
}
