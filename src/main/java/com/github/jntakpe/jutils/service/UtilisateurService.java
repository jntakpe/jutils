package com.github.jntakpe.jutils.service;

import com.github.jntakpe.fmk.service.GenericService;
import com.github.jntakpe.jutils.domain.Utilisateur;
import org.springframework.ldap.core.DirContextOperations;

/**
 * Traitement métiers associés à l'entité {@link Utilisateur}
 *
 * @author jntakpe
 */
public interface UtilisateurService extends GenericService<Utilisateur> {

    Utilisateur create(DirContextOperations ldapCtx);

    Utilisateur findByLogin(String login);

    Utilisateur findByNom(String nom);

    Utilisateur findByLdapNom(String nom);
}
