package com.github.jntakpe.jutils.repository;

import com.github.jntakpe.jutils.domain.Role;
import org.springframework.data.repository.CrudRepository;

/**
 * Interface définissant les méthodes permettant de manipuler l'entité {@link Role}
 *
 * @author jntakpe
 */
public interface RoleRepository extends CrudRepository<Role, Long> {

    /**
     * Récupère un {@link Role} à l'aide de son code
     *
     * @param code code du rôle
     * @return le rôle ou alors si aucun rôle ne correspond à ce code
     */
    Role findByCode(String code);
}
