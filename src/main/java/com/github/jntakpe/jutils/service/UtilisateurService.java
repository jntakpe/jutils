package com.github.jntakpe.jutils.service;

import com.github.jntakpe.fmk.service.GenericService;
import com.github.jntakpe.jutils.domain.Item;
import com.github.jntakpe.jutils.domain.Utilisateur;
import org.springframework.ldap.core.DirContextOperations;

import java.util.List;

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
     * Récupère tous les {@link Utilisateur} de COLO 2 dans le LDAP et les associes aux {@link Item}
     *
     * @param items liste des items récupérés précédemment
     * @return les items associés aux utilisateurs
     */
    List<Item> mapItemsAndUtilisateurs(List<Item> items);
}
