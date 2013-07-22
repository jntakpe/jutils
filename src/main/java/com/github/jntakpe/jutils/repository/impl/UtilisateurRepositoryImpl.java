package com.github.jntakpe.jutils.repository.impl;

import com.github.jntakpe.fmk.domain.Parameter;
import com.github.jntakpe.fmk.exception.BusinessCode;
import com.github.jntakpe.fmk.exception.BusinessException;
import com.github.jntakpe.fmk.service.ParameterService;
import com.github.jntakpe.fmk.util.constant.MandatoryParams;
import com.github.jntakpe.jutils.domain.Utilisateur;
import com.github.jntakpe.jutils.repository.UtilisateurRepositoryCustom;
import com.github.jntakpe.jutils.util.constants.UtilisateurLdapAttrs;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private LdapTemplate ldapTemplate;

    @Autowired
    private ParameterService parameterService;

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
    @Transactional
    public List<Utilisateur> findAllLdapUtilisateurs() {
        Parameter baseDnSopra = parameterService.findByKey(MandatoryParams.SOPRA_LDAP_BASEDN.getKey());
        if (baseDnSopra == null || StringUtils.isBlank(baseDnSopra.getValue()))
            throw new BusinessException(BusinessCode.LDAP_MISSING_PARAM, MandatoryParams.SOPRA_LDAP_BASEDN.getKey());
        Parameter baseDnSbs = parameterService.findByKey(MandatoryParams.SBS_LDAP_BASEDN.getKey());
        if (baseDnSbs == null || StringUtils.isBlank(baseDnSbs.getValue()))
            throw new BusinessException(BusinessCode.LDAP_MISSING_PARAM, MandatoryParams.SBS_LDAP_BASEDN.getKey());
        EqualsFilter filter = new EqualsFilter("objectClass", "person");
        UtilisateurAttributesMapper mapper = new UtilisateurAttributesMapper();
        List<Utilisateur> utilisateurs = ldapTemplate.search(baseDnSopra.getValue(), filter.encode(), mapper);
        utilisateurs.addAll(ldapTemplate.search(baseDnSbs.getValue(), filter.encode(), mapper));
        return utilisateurs;
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
                        if (lookupAttr != UtilisateurLdapAttrs.TELEPHONE && lookupAttr != UtilisateurLdapAttrs.ARRIVEE)
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
            utilisateur.setNomOutlook(attrsMap.get(UtilisateurLdapAttrs.OUTLOOK_PRENOM) + " " +
                    attrsMap.get(UtilisateurLdapAttrs.OUTLOOK_NOM).toUpperCase());
            String fullDate = attrsMap.get(UtilisateurLdapAttrs.ARRIVEE);
            if (fullDate != null) {
                String dateStr = fullDate.substring(0, fullDate.indexOf("."));
                utilisateur.setArriveeSopra(DateTimeFormat.forPattern("yyyyMMddHHmmss").parseDateTime(dateStr).toDate());
            }
            return utilisateur;
        }
    }
}
