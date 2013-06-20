package com.github.jntakpe.jutils.service;

import com.github.jntakpe.fmk.service.GenericService;
import com.github.jntakpe.jutils.domain.Role;

/**
 * Traitement métiers associés à l'entité {@link Role}
 *
 * @author jntakpe
 */
public interface RoleService extends GenericService<Role> {

    Role findByCode(String code);

}
