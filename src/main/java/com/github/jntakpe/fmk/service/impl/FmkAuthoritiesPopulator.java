package com.github.jntakpe.fmk.service.impl;

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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
     * @param ldapCtx données récupérées du LDAP
     * @param username login
     * @return les rôles associés à l'utilisateur
     */
    @Override
    @Transactional
    public Collection<? extends GrantedAuthority> getGrantedAuthorities(DirContextOperations ldapCtx, String username) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        Utilisateur utilisateur = utilisateurService.findByLogin(username);
        if (utilisateur == null)
            utilisateur = utilisateurService.create(ldapCtx);
        else if (utilisateur.getPremierAcces() == null)
            utilisateur.setPremierAcces(Instant.now().toDate());
        utilisateur.setDernierAcces(Instant.now().toDate());
        utilisateur.incrementNombreAccess();
        for (Role role : utilisateur.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getCode()));
        }
        return authorities;
    }
}
