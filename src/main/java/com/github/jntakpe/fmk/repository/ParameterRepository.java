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

    Parameter findByKey(String key);

}
