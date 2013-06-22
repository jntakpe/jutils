package com.github.jntakpe.jutils.repository;

import com.github.jntakpe.jutils.domain.Utilisateur;

import javax.naming.directory.Attributes;


/**
 * Repository accédant à des données de l'entité {@link com.github.jntakpe.jutils.domain.Utilisateur} via le LDAP
 *
 * @author jntakpe
 */
public interface UtilisateurRepositoryCustom {

    /**
     * Mappe le résultat d'un requête LDAP avec un {@link Utilisateur}
     *
     * @param attrs résultat de la requête LDAP
     * @return l'utilisateur ou null si un ou plusieurs attributs obligatoire sont introuvables
     */
    Utilisateur map(Attributes attrs);

    /**
     * Récupère l'{@link Utilisateur} dans le LDAP correspondant à ce nom
     *
     * @param nom nom de l'utilisateur recherché
     * @return l'utilisateur recherché ou null si aucun utilisateur ne correspond à ce nom
     */
    Utilisateur findByLdapNom(String nom);
}
