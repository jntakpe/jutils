package com.github.jntakpe.jutils.repository;

import com.github.jntakpe.jutils.domain.Role;
import org.springframework.data.repository.CrudRepository;

/**
 * Interface définissant les méthodes permettant de manipuler l'entité {@link Role}
 *
 * @author jntakpe
 */
public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findByCode(String code);
}
