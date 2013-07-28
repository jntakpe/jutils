package com.github.jntakpe.jutils.repository;

import com.github.jntakpe.jutils.domain.Commande;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;

/**
 * Interface définissant les méthodes permettant de manipuler l'entité {@link Commande}
 *
 * @author jntakpe
 */
public interface CommandeRepository extends CrudRepository<Commande, Long> {

    /**
     * Récupère la commande ouverture avant la date de fermetture
     *
     * @param cloture  date du jour
     * @param cloturee indique si la commande est cloturée
     * @return la commande ouverte sur cette plage ou null si aucune commande n'est ouverte
     */
    Commande findByClotureAfterAndCloturee(Date cloture, boolean cloturee);

}
