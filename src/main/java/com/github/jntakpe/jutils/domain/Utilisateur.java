package com.github.jntakpe.jutils.domain;

import com.github.jntakpe.fmk.domain.GenericDomain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Entité représentant un utilisateur
 *
 * @author jntakpe
 */
@Entity
@SequenceGenerator(name = "SG", sequenceName = "SEQ_USER")
public class Utilisateur extends GenericDomain {

    @Column(nullable = false, unique = true)
    String nom;

    @Column(nullable = false, unique = true)
    String login;

    @Column(nullable = false, unique = true)
    String mail;

    @Column(nullable = false, unique = true)
    String matricule;

    @Column(unique = true)
    private String telephone;

    private Date premierAcces;

    private Date dernierAcces;

    private Integer nombreAccess = 0;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "utilisateur_role", joinColumns = {
            @JoinColumn(referencedColumnName = "id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(referencedColumnName = "id", nullable = false, updatable = false)})
    private Set<Role> roles = new HashSet<>();

    @OneToMany
    private Set<Item> items = new HashSet<>();

    @Transient
    private String agence;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Date getPremierAcces() {
        return premierAcces;
    }

    public void setPremierAcces(Date premierAcces) {
        this.premierAcces = premierAcces;
    }

    public Date getDernierAcces() {
        return dernierAcces;
    }

    public void setDernierAcces(Date dernierAcces) {
        this.dernierAcces = dernierAcces;
    }

    public void incrementNombreAccess() {
        this.nombreAccess = nombreAccess++;
    }

    public Integer getNombreAccess() {
        return nombreAccess;
    }

    public void setNombreAccess(Integer nombreAccess) {
        this.nombreAccess = nombreAccess;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        this.getRoles().add(role);
    }

    public void removeRole(Role role) {
        this.getRoles().remove(role);
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    public String getAgence() {
        return agence;
    }

    public void setAgence(String agence) {
        this.agence = agence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Utilisateur that = (Utilisateur) o;

        if (!login.equals(that.login)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return login.hashCode();
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "nom='" + nom + '\'' +
                '}';
    }
}
