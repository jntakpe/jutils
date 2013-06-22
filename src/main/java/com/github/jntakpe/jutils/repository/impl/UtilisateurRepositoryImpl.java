package com.github.jntakpe.jutils.repository.impl;

import com.github.jntakpe.fmk.exception.BusinessCode;
import com.github.jntakpe.fmk.exception.BusinessException;
import com.github.jntakpe.jutils.domain.Utilisateur;
import com.github.jntakpe.jutils.repository.UtilisateurRepositoryCustom;
import com.github.jntakpe.jutils.util.constants.UtilisateurLdapAttrs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.WhitespaceWildcardsFilter;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implémentation de la récupération des données relatives à l'entité {@link Utilisateur} via le LDAP
 *
 * @author jntakpe
 */
public class UtilisateurRepositoryImpl implements UtilisateurRepositoryCustom {

    private static final String COLO_AGENCE = "TOULOUSE COLOMIERS 1";

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
    public Utilisateur findByLdapNom(String nom) {
        List<Utilisateur> utilisateurs = ldapTemplate.search("OU=UsersEmea",
                new WhitespaceWildcardsFilter("name", nom).encode(), new UtilisateurAttributesMapper());
        if (utilisateurs.size() == 0) {
            throw new BusinessException(BusinessCode.LDAP_USER_MISSING, nom);
        } else if (utilisateurs.size() != 1) {
            List<Utilisateur> utilisateurAgenceList = new ArrayList<>();
            for (Utilisateur utilisateur : utilisateurs) {
                if (COLO_AGENCE.equals(utilisateur.getAgence())) {
                    utilisateurAgenceList.add(utilisateur);
                }
            }
            if (utilisateurAgenceList.size() != 1) {
                if (utilisateurAgenceList.size() == 0)
                    throw new BusinessException(BusinessCode.LDAP_USER_MISSING, nom);
                else
                    throw new BusinessException(BusinessCode.LDAP_NO_SINGLE_RESULT, nom);
            }
            return utilisateurAgenceList.get(0);
        }
        return utilisateurs.get(0);
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
