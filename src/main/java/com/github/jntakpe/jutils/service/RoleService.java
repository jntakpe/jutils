package com.github.jntakpe.jutils.service;

import com.github.jntakpe.fmk.service.GenericService;
import com.github.jntakpe.jutils.domain.Role;

/**
 * Traitement métiers associés à l'entité {@link Role}
 *
 * @author jntakpe
 */
public interface RoleService extends GenericService<Role> {

    /**
     * Récupère un {@link Role} à l'aide de son code
     *
     * @param code code du rôle
     * @return le rôle ou null si aucun rôle ne correspond à ce code
     */
    Role findByCode(String code);

}
