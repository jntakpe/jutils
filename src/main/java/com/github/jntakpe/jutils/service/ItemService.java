package com.github.jntakpe.jutils.service;

import com.github.jntakpe.fmk.service.GenericService;
import com.github.jntakpe.jutils.domain.Item;

import java.util.List;

/**
 * Traitement métiers associés à l'entité {@link Item}
 *
 * @author jntakpe
 */
public interface ItemService extends GenericService<Item> {

    public List<Item> majLdapItems();

}
