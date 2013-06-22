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

    /**
     * Créé un {@link Utilisateur} à l'aide des informations récupérées depuis le ldap
     *
     * @param ldapCtx résultat de la requête ldap sur un utilisateur
     * @return l'utilisateur créé
     */
    Utilisateur create(DirContextOperations ldapCtx);

    /**
     * Récupère un {@link Utilisateur} à l'aide de son login
     *
     * @param login login de l'utilisateur
     * @return l'utilisateur ou null si aucun utilisateur ne correspond à ce login
     */
    Utilisateur findByLogin(String login);

    /**
     * Récupère un {@link Utilisateur} à l'aide de son nom
     *
     * @param nom nom de l'utilisateur
     * @return l'utilisateur ou null si aucun utilisateur ne correspond à ce nom
     */
    Utilisateur findByNom(String nom);

    /**
     * Récupère un {@link Utilisateur} dans le LDAP à l'aide de son nom
     *
     * @param nom nom de l'utilisateur
     * @return l'utilisateur ou null si aucun utilisateur ne correspond à ce nom
     */
    Utilisateur findByLdapNom(String nom);
}
