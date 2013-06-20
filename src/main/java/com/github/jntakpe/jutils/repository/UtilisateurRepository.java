package com.github.jntakpe.jutils.repository;

import com.github.jntakpe.jutils.domain.Utilisateur;
import org.springframework.data.repository.CrudRepository;

/**
 * Interface définissant les méthodes permettant de manipuler l'entité {@link Utilisateur}
 *
 * @author jntakpe
 */
public interface UtilisateurRepository extends CrudRepository<Utilisateur, Long> {

    Utilisateur findByLogin(String login);

    Utilisateur findByMail(String mail);

    Utilisateur findByMatricule(String matricule);

    Utilisateur findByTelephone(String telephone);

}
