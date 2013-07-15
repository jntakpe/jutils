package com.github.jntakpe.jutils.repository;

import com.github.jntakpe.jutils.domain.Cafe;
import org.springframework.data.repository.CrudRepository;

/**
 * Interface définissant les méthodes permettant de manipuler l'entité {@link Cafe}
 *
 * @author jntakpe
 */
public interface CafeRepository extends CrudRepository<Cafe, Long> {

    Cafe findByNom(String nom);

}
