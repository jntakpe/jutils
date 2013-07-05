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
    ARRIVEE("whenCreated"),
    AGENCE("l"),
    OUTLOOK_PRENOM("givenName"),
    OUTLOOK_NOM("sn");

    private final String attr;

    private UtilisateurLdapAttrs(String attr) {
        this.attr = attr;
    }

    public String getAttr() {
        return attr;
    }
}
