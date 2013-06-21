package com.github.jntakpe.jutils.repository;

import com.github.jntakpe.jutils.domain.Utilisateur;
import org.springframework.data.repository.CrudRepository;

/**
 * Interface définissant les méthodes permettant de manipuler l'entité {@link Utilisateur}
 *
 * @author jntakpe
 */
public interface UtilisateurRepository extends CrudRepository<Utilisateur, Long>, UtilisateurRepositoryCustom {

    Utilisateur findByLoginIgnoreCase(String login);

    Utilisateur findByNomIgnoreCase(String nom);

}
