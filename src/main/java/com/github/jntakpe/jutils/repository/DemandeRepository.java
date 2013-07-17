package com.github.jntakpe.jutils.repository;

import com.github.jntakpe.jutils.domain.Demande;
import org.springframework.data.repository.CrudRepository;

/**
 * Interface définissant les méthodes de manipulation de l'entité {@link Demande}
 *
 * @author jntakpe
 */
public interface DemandeRepository extends CrudRepository<Demande, Long> {

}
