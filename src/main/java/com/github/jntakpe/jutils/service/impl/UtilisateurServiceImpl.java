package com.github.jntakpe.jutils.service.impl;

import com.github.jntakpe.fmk.service.impl.GenericServiceImpl;
import com.github.jntakpe.fmk.util.constant.LdapAttrs;
import com.github.jntakpe.fmk.web.ConnexionController;
import com.github.jntakpe.jutils.domain.Role;
import com.github.jntakpe.jutils.domain.Utilisateur;
import com.github.jntakpe.jutils.repository.UtilisateurRepository;
import com.github.jntakpe.jutils.service.RoleService;
import com.github.jntakpe.jutils.service.UtilisateurService;
import org.joda.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;
import java.util.Set;

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
    public Utilisateur create(Map<LdapAttrs, String> ldapCtx) {
        Date now = Instant.now().toDate();
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setLogin(ldapCtx.get(LdapAttrs.LOGIN));
        utilisateur.setNom(ldapCtx.get(LdapAttrs.NOM));
        utilisateur.setMail(ldapCtx.get(LdapAttrs.MAIL));
        utilisateur.setMatricule(ldapCtx.get(LdapAttrs.MATRICULE));
        utilisateur.setTelephone(ldapCtx.get(LdapAttrs.TELEPHONE));
        utilisateur.setPremierAcces(now);
        utilisateur.setDernierAcces(now);
        utilisateur.addRole(roleService.findByCode(ConnexionController.BASIC_ROLE));
        return save(utilisateur);
    }

    @Override
    @Transactional(readOnly = true)
    public Utilisateur findByLogin(String login) {
        return utilisateurRepository.findByLogin(login);
    }
}
