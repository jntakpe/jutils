package com.github.jntakpe.jutils.service.impl;

import com.github.jntakpe.fmk.service.impl.GenericServiceImpl;
import com.github.jntakpe.jutils.domain.Role;
import com.github.jntakpe.jutils.repository.RoleRepository;
import com.github.jntakpe.jutils.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

/**
 * Implémentation des services associés à l'entité {@link Role}
 *
 * @author jntakpe
 */
@Service
public class RoleServiceImpl extends GenericServiceImpl<Role> implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public CrudRepository<Role, Long> getRepository() {
        return roleRepository;
    }

    @Override
    public Role findByCode(String code) {
        return roleRepository.findByCode(code);
    }
}
