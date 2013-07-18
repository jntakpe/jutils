package com.github.jntakpe.jutils.service.impl;

import com.github.jntakpe.fmk.service.impl.GenericServiceImpl;
import com.github.jntakpe.jutils.domain.Cafe;
import com.github.jntakpe.jutils.repository.CafeRepository;
import com.github.jntakpe.jutils.service.CafeService;
import com.github.jntakpe.jutils.util.constants.Categorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    @Transactional(readOnly = true)
    public List<Cafe> categorize() {
        List<Cafe> cafes = new ArrayList<>();
        for (Categorie categorie : Categorie.values())
            cafes.addAll(cafeRepository.findByCategorieOrderByIntensiteDesc(categorie));
        return cafes;
    }
}
