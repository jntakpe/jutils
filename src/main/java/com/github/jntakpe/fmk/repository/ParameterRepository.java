package com.github.jntakpe.fmk.repository;

import com.github.jntakpe.fmk.domain.Parameter;
import org.springframework.data.repository.CrudRepository;

/**
 * Interface de manipulation de l'entité {@link Parameter}
 *
 * @author jntakpe
 * @see org.springframework.data.repository.CrudRepository
 */
public interface ParameterRepository extends CrudRepository<Parameter, Long> {

    /**
     * Récupère en base de données le {@link Parameter} à l'aide de la clé donnée
     *
     * @param key clé du paramètre
     * @return le paramètre ou null si le paramètre n'existe pas
     */
    Parameter findByKey(String key);

}
