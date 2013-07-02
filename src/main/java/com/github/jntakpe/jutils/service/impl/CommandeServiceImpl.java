package com.github.jntakpe.jutils.service.impl;

import com.github.jntakpe.fmk.service.impl.GenericServiceImpl;
import com.github.jntakpe.jutils.domain.Commande;
import com.github.jntakpe.jutils.repository.CommandeRepository;
import com.github.jntakpe.jutils.service.CommandeService;
import org.joda.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Implémentation des services associés à l'entité {@link Commande}
 *
 * @author jntakpe
 */
@Service
public class CommandeServiceImpl extends GenericServiceImpl<Commande> implements CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;

    @Override
    @Transactional
    public boolean isOpenCmd() {
        Date now = Instant.now().toDate();
        return commandeRepository.findByFermetureAfter(now) != null;
    }

    @Override
    public CrudRepository<Commande, Long> getRepository() {
        return commandeRepository;
    }
}
