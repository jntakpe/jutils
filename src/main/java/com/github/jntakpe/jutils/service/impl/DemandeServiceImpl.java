package com.github.jntakpe.jutils.service.impl;

import com.github.jntakpe.fmk.service.impl.GenericServiceImpl;
import com.github.jntakpe.jutils.domain.Cafe;
import com.github.jntakpe.jutils.domain.Demande;
import com.github.jntakpe.jutils.domain.DemandeCafe;
import com.github.jntakpe.jutils.domain.DemandeCafeId;
import com.github.jntakpe.jutils.repository.DemandeCafeRepository;
import com.github.jntakpe.jutils.repository.DemandeRepository;
import com.github.jntakpe.jutils.service.CafeService;
import com.github.jntakpe.jutils.service.CommandeService;
import com.github.jntakpe.jutils.service.DemandeService;
import com.github.jntakpe.jutils.service.UtilisateurService;
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
    private DemandeCafeRepository demandeCafeRepository;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private CafeService cafeService;

    @Autowired
    private CommandeService commandeService;

    /**
     * @{inhericDoc}
     */
    @Override
    public CrudRepository<Demande, Long> getRepository() {
        return demandeRepository;
    }

    /**
     * @{inhericDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public DemandeDTO findDemandeAndCafes() {
        Demande demande = findByUtilisateur();
        DemandeDTO demandeDTO = new DemandeDTO();
        if (demande != null) demandeDTO.setDemande(demande);
        else demandeDTO.setDemande(new Demande());
        demandeDTO.setCafes(cafeService.initialize(demande));
        return demandeDTO;


    }

    /**
     * @{inhericDoc}
     */
    @Override
    @Transactional
    public Demande saveDemandeAndCafes(DemandeDTO demandeDTO) {
        Demande demande = demandeDTO.getDemande();
        demande.setCommande(commandeService.findOpenCmd());
        demande.setUtilisateur(utilisateurService.getCurrent());
        Demande managedDemande = save(demande);
        demandeCafeRepository.deleteByDemande(managedDemande);
        for (Cafe cafe : demandeDTO.getCafes()) {
            DemandeCafe demandeCafe = new DemandeCafe();
            demandeCafe.setNombreBoites(cafe.getNb());
            DemandeCafeId demandeCafeId = new DemandeCafeId();
            demandeCafeId.setDemande(managedDemande);
            demandeCafeId.setCafe(cafeService.findOne(cafe.getId()));
            demandeCafe.setDemandeCafeId(demandeCafeId);
            demandeCafeRepository.save(demandeCafe);
        }
        return demande;
    }

    /**
     * @{inhericDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Demande findByUtilisateur() {
        return demandeRepository.findByCommandeAndUtilisateur(commandeService.findOpenCmd(), utilisateurService.getCurrent());
    }
}
