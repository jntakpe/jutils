package com.github.jntakpe.jutils.domain;

import com.fasterxml.jackson.annotation.*;
import com.github.jntakpe.fmk.domain.GenericDomain;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Entité représentant un utilisateur
 *
 * @author jntakpe
 */
@Entity
@SequenceGenerator(name = "SG", sequenceName = "SEQ_UTILISATEUR")
public class Utilisateur extends GenericDomain {

    @Column(nullable = false, unique = true)
    String nom;

    @Column(nullable = false, unique = true)
    String login;

    @Column(nullable = false, unique = true)
    String mail;

    @Column(nullable = false, unique = true)
    String matricule;

    private String telephone;

    private Date premierAcces;

    private Date dernierAcces;

    private Integer nombreAcces = 0;

    private Date arriveeSopra;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "utilisateur_role", joinColumns = {
            @JoinColumn(referencedColumnName = "id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(referencedColumnName = "id", nullable = false, updatable = false)})
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "utilisateur")
    @JsonIgnoreProperties({"utilisateur"})
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

    public Integer getNombreAcces() {
        return nombreAcces;
    }

    public void setNombreAcces(Integer nombreAcces) {
        this.nombreAcces = nombreAcces;
    }

    public void incrementNombreAcces() {
        nombreAcces++;
    }

    public Date getArriveeSopra() {
        return arriveeSopra;
    }

    public void setArriveeSopra(Date arriveeSopra) {
        this.arriveeSopra = arriveeSopra;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    public void removeRole(Role role) {
        roles.remove(role);
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
        if (!(o instanceof Utilisateur)) return false;

        Utilisateur that = (Utilisateur) o;

        if (nom != null ? !nom.equals(that.nom) : that.nom != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return nom != null ? nom.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "nom='" + nom + '\'' +
                '}';
    }
}
