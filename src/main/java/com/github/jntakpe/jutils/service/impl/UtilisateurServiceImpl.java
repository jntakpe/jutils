package com.github.jntakpe.jutils.service.impl;

import com.github.jntakpe.fmk.service.impl.GenericServiceImpl;
import com.github.jntakpe.fmk.web.ConnexionController;
import com.github.jntakpe.jutils.domain.Item;
import com.github.jntakpe.jutils.domain.Utilisateur;
import com.github.jntakpe.jutils.repository.UtilisateurRepository;
import com.github.jntakpe.jutils.service.ItemService;
import com.github.jntakpe.jutils.service.RoleService;
import com.github.jntakpe.jutils.service.UtilisateurService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implémentation des services associés à l'entité {@link Utilisateur}
 *
 * @author jntakpe
 */
@Service
public class UtilisateurServiceImpl extends GenericServiceImpl<Utilisateur> implements UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ItemService itemService;

    @Override
    public CrudRepository<Utilisateur, Long> getRepository() {
        return utilisateurRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public List<Item> mapItemsAndUtilisateurs(List<Item> items) {

        List<Utilisateur> utilisateurs = utilisateurRepository.findAllLdapUtilisateurs();
        Map<String, Utilisateur> utilisateurMap = new HashMap<>(utilisateurs.size());
        for (Utilisateur utilisateur : utilisateurs) { //On créé une map pour indexer les utilisateurs
            String nom = utilisateur.getNom();
            nom = Normalizer.normalize(nom, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").replaceAll("\\W", " ");
            utilisateurMap.put(nom, utilisateur);
        }
        for (Item item : items) {
            if (utilisateurMap.containsKey(item.getDescription().trim())) {
                Utilisateur utilisateur = utilisateurMap.get(item.getDescription().trim());
                Utilisateur managedUtilisateur = findByLogin(utilisateur.getLogin());
                utilisateur.setTelephone(StringUtils.deleteWhitespace(utilisateur.getTelephone()));
                if (managedUtilisateur == null) {
                    managedUtilisateur = save(utilisateur);
                } else {
                    managedUtilisateur.setNom(utilisateur.getNom());
                    managedUtilisateur.setAgence(utilisateur.getAgence());
                    managedUtilisateur.setItems(utilisateur.getItems());
                    managedUtilisateur.setLogin(utilisateur.getLogin());
                    managedUtilisateur.setMail(utilisateur.getMail());
                    managedUtilisateur.setMatricule(utilisateur.getMatricule());
                    managedUtilisateur.setTelephone(utilisateur.getTelephone());
                }
                item.setUtilisateur(managedUtilisateur);
            }
        }
        return items;
    }

    /**
     * @{inhericDoc}
     */
    @Override
    @Transactional
    public Utilisateur create(DirContextOperations ldapCtx) {
        Utilisateur utilisateur = utilisateurRepository.map(ldapCtx.getAttributes());
        utilisateur.addRole(roleService.findByCode(ConnexionController.BASIC_ROLE));
        return save(utilisateur);
    }

    /**
     * @{inhericDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Utilisateur findByLogin(String login) {
        return utilisateurRepository.findByLoginIgnoreCase(login);
    }
}
