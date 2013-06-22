package com.github.jntakpe.jutils.service.impl;

import com.github.jntakpe.fmk.exception.BusinessException;
import com.github.jntakpe.fmk.service.MessageManager;
import com.github.jntakpe.fmk.service.impl.GenericServiceImpl;
import com.github.jntakpe.fmk.util.constant.LogLevel;
import com.github.jntakpe.jutils.domain.Item;
import com.github.jntakpe.jutils.domain.Utilisateur;
import com.github.jntakpe.jutils.repository.ItemRepository;
import com.github.jntakpe.jutils.service.ItemService;
import com.github.jntakpe.jutils.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implémentation des services associés à l'entité {@link Item}
 *
 * @author jntakpe
 */
@Service
public class ItemServiceImpl extends GenericServiceImpl<Item> implements ItemService {

    @Autowired
    private MessageManager messageManager;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UtilisateurService utilisateurService;

    @Override
    public CrudRepository<Item, Long> getRepository() {
        return itemRepository;
    }

    /**
     * @{inhericDoc}
     */
    @Override
    @Transactional
    public void majLdapItems() {
        for (Item ldapItem : itemRepository.findAllLdapItems()) {
            Item item = itemRepository.findByNom(ldapItem.getNom());
            if (item == null) //Le poste de travail n'existe pas en DB
                resolveUser(ldapItem);
            else {
                Utilisateur utilisateur = item.getUtilisateur();
                String ldapNom = ldapItem.getDescription().substring(0, ldapItem.getDescription().indexOf("-")).trim();
                if (!ldapNom.equals(utilisateur.getNom()))  //Si l'item existe mais n'a pas le bon utilisateur associé
                    resolveUser(ldapItem);

            }
        }
    }

    /**
     * Retrouve l'utilisateur associé à l'item et les persiste
     *
     * @param ldapItem item a persister
     */
    private void resolveUser(Item ldapItem) {
        String nom = ldapItem.getDescription().substring(0, ldapItem.getDescription().indexOf("-")).trim();
        Utilisateur utilisateur = utilisateurService.findByNom(nom);
        if (utilisateur == null) { //L'utilisateur associé au poste de travail n'existe pas en DB
            Utilisateur ldapUtilisateur;
            try {
                ldapUtilisateur = utilisateurService.findByLdapNom(nom);
            } catch (BusinessException e) {
                e.printStackTrace();
                messageManager.logMessage(e.getErrorCode(), LogLevel.WARN, e.getParams());
                return;
            }
            utilisateur = utilisateurService.save(ldapUtilisateur);
        }
        ldapItem.setUtilisateur(utilisateur);
        save(ldapItem);
        messageManager.logMessage("MSG10000", LogLevel.INFO, ldapItem.getNom());
    }


}
