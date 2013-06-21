package com.github.jntakpe.jutils.repository;

import com.github.jntakpe.jutils.domain.Utilisateur;

import javax.naming.directory.Attributes;


/**
 * Repository accédant à des données de l'entité {@link com.github.jntakpe.jutils.domain.Utilisateur} via le LDAP
 *
 * @author jntakpe
 */
public interface UtilisateurRepositoryCustom {

    Utilisateur map(Attributes attrs);

    Utilisateur findByLdapNom(String nom);
}
