package com.github.jntakpe.jutils.service;

import com.github.jntakpe.fmk.service.GenericService;
import com.github.jntakpe.jutils.domain.Item;

/**
 * Traitement métiers associés à l'entité {@link Item}
 *
 * @author jntakpe
 */
public interface ItemService extends GenericService<Item> {

    /**
     * Insère en base de données tous les {@link Item} de COLO 2 depuis le ldap et les associe à leur propriétaire
     */
    void saveLdapItems();

    /**
     * Récupère un {@link Item} par son nom
     *
     * @param nom nom de l'item
     * @return l'item correspondant à ce nom ou alors null
     */
    Item findByNom(String nom);
}
