package com.github.jntakpe.jutils.service;

import com.github.jntakpe.fmk.service.GenericService;
import com.github.jntakpe.jutils.domain.Item;
import com.github.jntakpe.jutils.domain.Utilisateur;
import com.github.jntakpe.jutils.util.dto.UtilisateurRoleDTO;
import org.springframework.ldap.core.DirContextOperations;

import java.util.List;
import java.util.Set;

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
     * Récupère un {@link Utilisateur} à l'aide de son mail
     *
     * @param mail mail de l'utilisateur
     * @return l'utilisateur ou null si aucun utilisateur ne correspond à ce mail
     */
    Utilisateur findByMail(String mail);

    /**
     * Récupère tous les {@link Utilisateur} de COLO 2 dans le LDAP et les associes aux {@link Item}
     *
     * @param items liste des items récupérés précédemment
     * @return les items associés aux utilisateurs
     */
    List<Item> mapItemsAndUtilisateurs(List<Item> items);

    /**
     * Récupère tous les {@link Utilisateur} et initialise la liste des {@link Item} associés
     *
     * @return la liste des utilisateurs
     */
    Iterable<Utilisateur> findAllUtilisateursWithItems();

    /**
     * Récupère tous les {@link Utilisateur} et initialise la liste des {@link com.github.jntakpe.jutils.domain.Role} associés
     *
     * @return un set d'utilisateur mappé avec les rôles
     */
    Set<UtilisateurRoleDTO> findAllUtilisateursWithRoles();

    /**
     * Récupère un {@link Utilisateur} à l'aide de son login et récupère ses rôles et items
     *
     * @param login login de l'utilisateur
     * @return l'utilisateur totalement initialisé
     */
    Utilisateur findByLoginAndInitialize(String login);

    /**
     * Renvoi l'utilisateur actuellement connecté
     *
     * @return utilisateur connecté
     */
    Utilisateur getCurrent();
}
