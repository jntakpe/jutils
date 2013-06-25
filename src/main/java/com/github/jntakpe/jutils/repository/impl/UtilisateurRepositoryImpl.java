package com.github.jntakpe.jutils.repository.impl;

import com.github.jntakpe.fmk.exception.BusinessCode;
import com.github.jntakpe.fmk.exception.BusinessException;
import com.github.jntakpe.jutils.domain.Utilisateur;
import com.github.jntakpe.jutils.repository.UtilisateurRepositoryCustom;
import com.github.jntakpe.jutils.util.constants.UtilisateurLdapAttrs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implémentation de la récupération des données relatives à l'entité {@link Utilisateur} via le LDAP
 *
 * @author jntakpe
 */
public class UtilisateurRepositoryImpl implements UtilisateurRepositoryCustom {

    @Autowired
    private LdapTemplate ldapTemplate;

    /**
     * {@inheritDoc}
     */
    @Override
    public Utilisateur map(Attributes attrs) {
        return (Utilisateur) new UtilisateurAttributesMapper().mapFromAttributes(attrs);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Utilisateur> findAllLdapUtilisateurs() {
        AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("objectClass", "person")).and(new EqualsFilter("l", "TOULOUSE COLOMIERS 1"));
        return ldapTemplate.search("OU=UsersEmea", filter.encode(), new UtilisateurAttributesMapper());
    }

    /**
     * Inner classe permettant de mapper le résultat d'une requête LDAP avec un {@link Utilisateur}
     */
    private class UtilisateurAttributesMapper implements AttributesMapper {

        public Object mapFromAttributes(Attributes attrs) {
            Map<UtilisateurLdapAttrs, String> attrsMap = new HashMap<>();
            for (UtilisateurLdapAttrs lookupAttr : UtilisateurLdapAttrs.values()) {
                try {
                    Attribute attr = attrs.get(lookupAttr.getAttr());
                    if (attr == null) {
                        if (lookupAttr != UtilisateurLdapAttrs.TELEPHONE)
                            throw new BusinessException(BusinessCode.LDAP_USER_ATTR_MISSING, lookupAttr, attrs);
                    } else {
                        attrsMap.put(lookupAttr, (String) attr.get());
                    }
                } catch (NamingException e) {
                    throw new BusinessException(BusinessCode.LDAP_USER_ATTR_MISSING, lookupAttr, attrs);
                }
            }

            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setLogin(attrsMap.get(UtilisateurLdapAttrs.LOGIN));
            utilisateur.setNom(attrsMap.get(UtilisateurLdapAttrs.NOM));
            utilisateur.setMail(attrsMap.get(UtilisateurLdapAttrs.MAIL));
            utilisateur.setMatricule(attrsMap.get(UtilisateurLdapAttrs.MATRICULE));
            utilisateur.setTelephone(attrsMap.get(UtilisateurLdapAttrs.TELEPHONE));
            utilisateur.setAgence(attrsMap.get(UtilisateurLdapAttrs.AGENCE));
            return utilisateur;
        }
    }
}
