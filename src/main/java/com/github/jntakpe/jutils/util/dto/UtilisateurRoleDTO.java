package com.github.jntakpe.jutils.util.dto;

import com.github.jntakpe.jutils.domain.Role;

import java.util.HashSet;
import java.util.Set;

/**
 * Objet de transfert pour mapper un utilisateur avec ses rôles
 *
 * @author jntakpe
 */
public class UtilisateurRoleDTO {

    private Long id;

    private String nom;

    private Set<Role> roles = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UtilisateurRoleDTO)) return false;

        UtilisateurRoleDTO that = (UtilisateurRoleDTO) o;

        if (nom != null ? !nom.equals(that.nom) : that.nom != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return nom != null ? nom.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "UtilisateurRoleDTO{" +
                "nom='" + nom + '\'' +
                '}';
    }
}
