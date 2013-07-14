package com.github.jntakpe.jutils.service.impl;

import com.github.jntakpe.fmk.service.impl.GenericServiceImpl;
import com.github.jntakpe.jutils.domain.Cafe;
import com.github.jntakpe.jutils.repository.CafeRepository;
import com.github.jntakpe.jutils.service.CafeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

/**
 * Implémentation des services associés à l'entité {@Cafe}
 *
 * @author jntakpe
 */
@Service
public class CafeServiceImpl extends GenericServiceImpl<Cafe> implements CafeService {

    @Autowired
    private CafeRepository cafeRepository;

    @Override
    public CrudRepository<Cafe, Long> getRepository() {
        return cafeRepository;
    }
}
