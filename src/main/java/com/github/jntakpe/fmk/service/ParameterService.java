package com.github.jntakpe.fmk.service;

import com.github.jntakpe.fmk.domain.Parameter;

/**
 * Services associés à l'entité {@link Parameter}
 *
 * @author jntakpe
 * @see GenericService
 */
public interface ParameterService extends GenericService<Parameter> {

    Parameter findByKey(String key);

}
