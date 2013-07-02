package com.github.jntakpe.jutils.repository;

import com.github.jntakpe.jutils.domain.Commande;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;

/**
 * Interface définissant les méthodes permettant de manipuler l'entité {@link Commande}
 *
 * @author jntakpe
 */
public interface CommandeRepository extends CrudRepository<Commande, Long> {

    Commande findByFermetureAfter(Date fermeture);

}
