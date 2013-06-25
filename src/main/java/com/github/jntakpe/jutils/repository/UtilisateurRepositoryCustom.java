package com.github.jntakpe.jutils.repository;

import com.github.jntakpe.jutils.domain.Utilisateur;

import javax.naming.directory.Attributes;
import java.util.List;


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
     * Récupère tous les {@link Utilisateur} de COLO 2
     *
     * @return liste des utilisateurs de COLO2
     */
    List<Utilisateur> findAllLdapUtilisateurs();
}
