package com.github.jntakpe.jutils.service;

import com.github.jntakpe.fmk.service.GenericService;
import com.github.jntakpe.jutils.domain.Commande;

/**
 * Services associés à une {@link Commande}
 *
 * @author jntakpe
 */
public interface CommandeService extends GenericService<Commande> {

    /**
     * Recherche des commandes ouvertes sur la plage actuelle
     *
     * @return true si une commande existe déjà sur la plage actuelle
     */
    boolean isOpenCmd();
}
