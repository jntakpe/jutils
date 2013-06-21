package com.github.jntakpe.jutils.repository;

import com.github.jntakpe.jutils.domain.Item;

import java.util.List;

/**
 * Repository accédant à des données de l'entité {@link com.github.jntakpe.jutils.domain.Item} via le LDAP
 *
 * @author jntakpe
 */
public interface ItemRepositoryCustom {

    List<Item> findAllLdapItems();

}
