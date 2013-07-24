package com.github.jntakpe.jutils.service.impl;

import com.github.jntakpe.fmk.service.impl.GenericServiceImpl;
import com.github.jntakpe.jutils.domain.Demande;
import com.github.jntakpe.jutils.repository.DemandeRepository;
import com.github.jntakpe.jutils.service.CafeService;
import com.github.jntakpe.jutils.service.DemandeService;
import com.github.jntakpe.jutils.util.dto.DemandeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implémentation des services associés à l'entité {@link Demande}
 *
 * @author jntakpe
 */
@Service
public class DemandeServiceImpl extends GenericServiceImpl<Demande> implements DemandeService {

    @Autowired
    private DemandeRepository demandeRepository;

    @Autowired
    private CafeService cafeService;

    @Override
    public CrudRepository<Demande, Long> getRepository() {
        return demandeRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public DemandeDTO findDemandeAndCafes(Long demandeId) {
        DemandeDTO demandeDTO = new DemandeDTO();
        if (demandeId != null) demandeDTO.setDemande(findOne(demandeId));
        else demandeDTO.setDemande(new Demande());
        demandeDTO.setCafes(cafeService.categorize());
        return demandeDTO;
    }
}
