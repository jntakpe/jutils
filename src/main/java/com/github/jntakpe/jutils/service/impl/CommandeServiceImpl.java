package com.github.jntakpe.jutils.service.impl;

import com.github.jntakpe.fmk.service.impl.GenericServiceImpl;
import com.github.jntakpe.jutils.domain.Commande;
import com.github.jntakpe.jutils.domain.Rib;
import com.github.jntakpe.jutils.domain.Utilisateur;
import com.github.jntakpe.jutils.repository.CommandeRepository;
import com.github.jntakpe.jutils.service.CommandeService;
import com.github.jntakpe.jutils.service.UtilisateurService;
import org.joda.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implémentation des services associés à l'entité {@link Commande}
 *
 * @author jntakpe
 */
@Service
public class CommandeServiceImpl extends GenericServiceImpl<Commande> implements CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private UtilisateurService utilisateurService;

    /**
     * @{inhericDoc}
     */
    @Override
    public Commande findOpenCmd() {
        return commandeRepository.findByClotureAfter(Instant.now().toDate());
    }

    /**
     * @{inhericDoc}
     */
    @Override
    @Transactional
    public Commande create(Commande commande, Rib rib) {
        Utilisateur creator = utilisateurService.getCurrent();
        creator.setRib(rib);
        commande.setResponsable(creator);
        return save(commande);
    }

    @Override
    public CrudRepository<Commande, Long> getRepository() {
        return commandeRepository;
    }
}
