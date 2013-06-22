package com.github.jntakpe.jutils.repository;

import com.github.jntakpe.jutils.domain.Item;

import java.util.List;

/**
 * Repository accédant à des données de l'entité {@link Item} via le LDAP
 *
 * @author jntakpe
 */
public interface ItemRepositoryCustom {

    /**
     * Récupère tous les {@link Item} de COLO 2 depuis le LDAP
     * @return la liste de tous les items
     */
    List<Item> findAllLdapItems();

}
