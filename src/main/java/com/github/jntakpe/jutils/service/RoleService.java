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

    /**
     * Assignation ou destitution d'un rôle à un utilisateur
     *
     * @param utilisateurId identifiant de l'utilisateur
     * @param roleName      nom du rôle
     * @param hasRole       true si l'utilisateur a déjà le rôle dans ce cas on doit lui enlever
     * @return le nom de l'utilisateur modifié
     */
    String assignRole(Long utilisateurId, String roleName, boolean hasRole);
}
