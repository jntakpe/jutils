package com.github.jntakpe.jutils.service.impl;

import com.github.jntakpe.fmk.service.impl.GenericServiceImpl;
import com.github.jntakpe.fmk.web.ConnexionController;
import com.github.jntakpe.jutils.domain.Utilisateur;
import com.github.jntakpe.jutils.repository.UtilisateurRepository;
import com.github.jntakpe.jutils.service.RoleService;
import com.github.jntakpe.jutils.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public CrudRepository<Utilisateur, Long> getRepository() {
        return utilisateurRepository;
    }

    @Override
    @Transactional
    public Utilisateur create(DirContextOperations ldapCtx) {
        Utilisateur utilisateur = utilisateurRepository.map(ldapCtx.getAttributes());
        utilisateur.addRole(roleService.findByCode(ConnexionController.BASIC_ROLE));
        return save(utilisateur);
    }

    @Override
    @Transactional(readOnly = true)
    public Utilisateur findByLogin(String login) {
        return utilisateurRepository.findByLoginIgnoreCase(login);
    }

    @Override
    @Transactional(readOnly = true)
    public Utilisateur findByNom(String nom) {
        return utilisateurRepository.findByNomIgnoreCase(nom);
    }

    @Override
    @Transactional(readOnly = true)
    public Utilisateur findByLdapNom(String nom) {
        return utilisateurRepository.findByLdapNom(nom);
    }
}
