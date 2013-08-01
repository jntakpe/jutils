package com.github.jntakpe.jutils.service.impl;

import com.github.jntakpe.fmk.service.impl.GenericServiceImpl;
import com.github.jntakpe.jutils.domain.Role;
import com.github.jntakpe.jutils.domain.Utilisateur;
import com.github.jntakpe.jutils.repository.RoleRepository;
import com.github.jntakpe.jutils.service.RoleService;
import com.github.jntakpe.jutils.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implémentation des services associés à l'entité {@link Role}
 *
 * @author jntakpe
 */
@Service
public class RoleServiceImpl extends GenericServiceImpl<Role> implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UtilisateurService utilisateurService;

    /**
     * @{inhericDoc}
     */
    @Override
    public CrudRepository<Role, Long> getRepository() {
        return roleRepository;
    }

    /**
     * @{inhericDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Role findByCode(String code) {
        return roleRepository.findByCode(code);
    }

    /**
     * @{inhericDoc}
     */
    @Override
    @Transactional
    public String assignRole(Long utilisateurId, String roleName, boolean hasRole) {
        Utilisateur utilisateur = utilisateurService.findOne(utilisateurId);
        Role role = findByCode(roleName);
        if (hasRole) utilisateur.removeRole(role);
        else utilisateur.addRole(role);
        return utilisateur.getNom();
    }

}
