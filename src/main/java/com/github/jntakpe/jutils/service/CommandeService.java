package com.github.jntakpe.jutils.service;

import com.github.jntakpe.fmk.service.GenericService;
import com.github.jntakpe.jutils.domain.Commande;
import com.github.jntakpe.jutils.domain.Rib;

/**
 * Services associés à une {@link Commande}
 *
 * @author jntakpe
 */
public interface CommandeService extends GenericService<Commande> {

    /**
     * Créé une commande
     *
     * @param commande commande a créer
     * @param rib      rib du créateur si nécessaire
     * @return la commandé créée
     */
    Commande create(Commande commande, Rib rib);

    /**
     * Recupère la commande ouverte
     *
     * @return la commande actuelement ouverte
     */
    Commande findOpenCmd();

    /**
     * Clôture la commande courante
     *
     * @param id identifiant de la commande courante
     * @return la commande clôturée
     */
    Commande cloture(Long id);

}
