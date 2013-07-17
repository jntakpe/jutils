package com.github.jntakpe.jutils.service.impl;

import com.github.jntakpe.fmk.service.impl.GenericServiceImpl;
import com.github.jntakpe.jutils.domain.Demande;
import com.github.jntakpe.jutils.repository.DemandeRepository;
import com.github.jntakpe.jutils.service.DemandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

/**
 * Implémentation des services associés à l'entité {@link Demande}
 *
 * @author jntakpe
 */
@Service
public class DemandeServiceImpl extends GenericServiceImpl<Demande> implements DemandeService {

    @Autowired
    private DemandeRepository demandeRepository;

    @Override
    public CrudRepository<Demande, Long> getRepository() {
        return demandeRepository;
    }
}
