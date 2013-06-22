package com.github.jntakpe.jutils.repository;

import com.github.jntakpe.jutils.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface définissant les méthodes permettant de manipuler l'entité {@link Item}
 *
 * @author jntakpe
 */
public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {

    /**
     * Récupère un {@link Item} à l'aide de son nom
     *
     * @param nom nom de l'item
     * @return l'item récupéré ou null si il n'existe aucun item correspondant à ce nom
     */
    Item findByNom(String nom);
}
