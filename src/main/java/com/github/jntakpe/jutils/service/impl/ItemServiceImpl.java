package com.github.jntakpe.jutils.service.impl;

import com.github.jntakpe.fmk.service.MessageManager;
import com.github.jntakpe.fmk.service.impl.GenericServiceImpl;
import com.github.jntakpe.fmk.util.constant.LogLevel;
import com.github.jntakpe.jutils.domain.Item;
import com.github.jntakpe.jutils.repository.ItemRepository;
import com.github.jntakpe.jutils.service.ItemService;
import com.github.jntakpe.jutils.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implémentation des services associés à l'entité {@link Item}
 *
 * @author jntakpe
 */
@Service
public class ItemServiceImpl extends GenericServiceImpl<Item> implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private MessageManager messageManager;

    @Override
    public CrudRepository<Item, Long> getRepository() {
        return itemRepository;
    }

    /**
     * @{inhericDoc}
     */
    @Override
    @Transactional
    public void saveLdapItems() {
        messageManager.logMessage("MSG10000", LogLevel.INFO);
        itemRepository.deleteAllInBatch();
        List<Item> items = utilisateurService.mapItemsAndUtilisateurs(itemRepository.findAllLdapItems());
        itemRepository.save(items);
        messageManager.logMessage("MSG10001", LogLevel.INFO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Item findByNom(String nom) {
        return itemRepository.findByNom(nom);
    }
}
