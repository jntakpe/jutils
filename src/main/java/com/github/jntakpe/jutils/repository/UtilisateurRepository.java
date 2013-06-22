package com.github.jntakpe.jutils.repository;

import com.github.jntakpe.jutils.domain.Utilisateur;
import org.springframework.data.repository.CrudRepository;

/**
 * Interface définissant les méthodes permettant de manipuler l'entité {@link Utilisateur}
 *
 * @author jntakpe
 */
public interface UtilisateurRepository extends CrudRepository<Utilisateur, Long>, UtilisateurRepositoryCustom {

    /**
     * Récupère un {@link Utilisateur} en fonction de son login en ignorant la case
     *
     * @param login login de l'utilisateur
     * @return l'utilisateur ou null si aucun utilisateur ne correspont à ce login
     */
    Utilisateur findByLoginIgnoreCase(String login);

    /**
     * Récupère un {@link Utilisateur} en fonction de son nom en ignorant la case
     *
     * @param nom nom de l'utilisateur
     * @return l'utilisateur ou null si aucun utilisateur ne correspont à ce nom
     */
    Utilisateur findByNomIgnoreCase(String nom);

}
