package com.github.jntakpe.fmk.service.impl;

import com.github.jntakpe.fmk.exception.BusinessCode;
import com.github.jntakpe.fmk.exception.BusinessException;
import com.github.jntakpe.fmk.util.constant.LdapAttrs;
import com.github.jntakpe.jutils.domain.Role;
import com.github.jntakpe.jutils.domain.Utilisateur;
import com.github.jntakpe.jutils.service.UtilisateurService;
import org.joda.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.NamingException;
import java.util.*;

/**
 * Classe permettant de récupérer un utilisateur dans le LDAP et de l'associer avec des rôles récupérés en base de
 * données. Si l'utilisateur n'est pas trouvé en base de données, on le créé et on y associe le rôle (ROLE_USER).
 *
 * @author jntakpe
 */
@Service
public class FmkAuthoritiesPopulator implements LdapAuthoritiesPopulator {

    @Autowired
    private UtilisateurService utilisateurService;

    /**
     * Association des rôles entre l'utilisateur récupérer du LDAP et celui persisté en base de données
     *
     * @param userData données récupérées du LDAP
     * @param username login
     * @return les rôles associés à l'utilisateur
     */
    @Override
    @Transactional
    public Collection<? extends GrantedAuthority> getGrantedAuthorities(DirContextOperations userData, String username) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        Utilisateur utilisateur = utilisateurService.findByLogin(username);
        if (utilisateur == null) {
            Map<LdapAttrs, String> ldapCtx = new HashMap<>();
            for (LdapAttrs mandatoryAttr : LdapAttrs.values()) {
                try {
                    ldapCtx.put(mandatoryAttr, userData.getAttributes().get(mandatoryAttr.getAttr()).get().toString());
                } catch (NamingException e) {
                    e.printStackTrace();
                    throw new BusinessException(BusinessCode.LDAP_MISSING_ATTR, mandatoryAttr, userData);
                }
            }
            utilisateur = utilisateurService.create(ldapCtx);
        } else {
            utilisateur.setDernierAcces(Instant.now().toDate());
        }
        for (Role role : utilisateur.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getCode()));
        }
        return authorities;
    }
}
