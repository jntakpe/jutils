package com.github.jntakpe.jutils.service;

import com.github.jntakpe.fmk.service.GenericService;
import com.github.jntakpe.fmk.util.constant.LdapAttrs;
import com.github.jntakpe.jutils.domain.Role;
import com.github.jntakpe.jutils.domain.Utilisateur;

import javax.naming.directory.Attributes;
import java.util.Map;
import java.util.Set;

/**
 * Traitement métiers associés à l'entité {@link Utilisateur}
 *
 * @author jntakpe
 */
public interface UtilisateurService extends GenericService<Utilisateur> {

    Utilisateur create(Map<LdapAttrs, String> ldapCtx);

    Utilisateur findByLogin(String login);

}
