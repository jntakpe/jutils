package com.github.jntakpe.jutils.service.impl;

import com.github.jntakpe.fmk.service.impl.GenericServiceImpl;
import com.github.jntakpe.jutils.domain.Item;
import com.github.jntakpe.jutils.domain.Utilisateur;
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

    @Override
    public CrudRepository<Item, Long> getRepository() {
        return itemRepository;
    }

    @Override
    @Transactional
    public List<Item> majLdapItems() {
        itemRepository.deleteAllInBatch();
        for (Item ldapItem : itemRepository.findAllLdapItems()) {
            Item item = itemRepository.findByNom(ldapItem.getNom());
            if (item == null) {
                String nomUser = ldapItem.getDescription().substring(0, ldapItem.getDescription().indexOf("-")).trim();
                Utilisateur utilisateur = utilisateurService.findByNom(nomUser);
                if (utilisateur == null) {
                    Utilisateur ldapUser = utilisateurService.findByLdapNom(nomUser);
                    if (ldapUser == null)
                        continue;
                    utilisateur = utilisateurService.save(ldapUser);
                }
                ldapItem.setUtilisateur(utilisateur);
                item = save(ldapItem);
            } else {

            }
        }
        return null;
    }

}
