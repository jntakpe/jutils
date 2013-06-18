package com.github.jntakpe.fmk.service.impl;

import com.github.jntakpe.fmk.domain.Parameter;
import com.github.jntakpe.fmk.repository.ParameterRepository;
import com.github.jntakpe.fmk.service.ParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implémentation des services associés à l'entité {@link Parameter}
 *
 * @author jntakpe
 * @see GenericServiceImpl
 */
@Service
public class ParameterServiceImpl extends GenericServiceImpl<Parameter> implements ParameterService {

    @Autowired
    private ParameterRepository parameterRepository;

    @Override
    public CrudRepository<Parameter, Long> getRepository() {
        return parameterRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Parameter findByKey(String key) {
        return parameterRepository.findByKey(key);
    }

}
