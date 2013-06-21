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

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LdapTemplate ldapTemplate;

    @Override
    public Utilisateur map(Attributes attrs) {
        return (Utilisateur) new UtilisateurAttributesMapper().mapFromAttributes(attrs);
    }

    @Override
    public Utilisateur findByLdapNom(String nom) {
        List<Utilisateur> utilisateurs = ldapTemplate.search("OU=UsersEmea",
                new WhitespaceWildcardsFilter("cn", nom).encode(), new UtilisateurAttributesMapper());
        if (utilisateurs.size() == 0) {
            logger.warn("Impossible de retrouver l'utilisateur {}", nom);
            return null;
        } else if (utilisateurs.size() != 1) {
            List<Utilisateur> utilisateurAgenceList = new ArrayList<>();
            for (Utilisateur utilisateur : utilisateurs) {
                if (COLO_AGENCE.equals(utilisateur.getAgence())) {
                    utilisateurAgenceList.add(utilisateur);
                }
            }
            if (utilisateurAgenceList.size() != 1) {
                if (utilisateurAgenceList.size() == 0)
                    logger.warn("Impossible de retrouver l'utilisateur {} répondant aux critères de recherche", nom);
                else
                    logger.warn("Impossible d'identifier l'utilisateur {} dans la liste de résultats", nom);
            }
            return utilisateurAgenceList.get(0);
        }
        return utilisateurs.get(0);
    }

    private class UtilisateurAttributesMapper implements AttributesMapper {

        public Object mapFromAttributes(Attributes attrs) {
            Map<UtilisateurLdapAttrs, String> attrsMap = new HashMap<>();
            for (UtilisateurLdapAttrs lookupAttr : UtilisateurLdapAttrs.values()) {
                try {
                    Attribute attr = attrs.get(lookupAttr.getAttr());
                    if (attr == null) {
                        if (lookupAttr != UtilisateurLdapAttrs.TELEPHONE || lookupAttr != UtilisateurLdapAttrs.AGENCE)
                            throw new BusinessException(BusinessCode.LDAP_USER_MISSING, lookupAttr, attrs);
                    } else {
                        attrsMap.put(lookupAttr, (String) attr.get());
                    }
                } catch (NamingException e) {
                    e.printStackTrace();
                    throw new BusinessException(BusinessCode.LDAP_USER_MISSING, lookupAttr, attrs);
                } catch (Exception e) {
                    //TODO a enlever rapidement meme très rapidement
                    e.printStackTrace();
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
