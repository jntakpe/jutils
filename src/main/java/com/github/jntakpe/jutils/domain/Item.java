package com.github.jntakpe.jutils.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.jntakpe.fmk.domain.GenericDomain;

import javax.persistence.*;
import java.util.Date;

/**
 * Entité représentant un poste de travail
 *
 * @author jntakpe
 */
@Entity
@SequenceGenerator(name = "SG", sequenceName = "SEQ_ITEM")
public class Item extends GenericDomain {

    @Column(nullable = false, unique = true)
    private String nom;

    private Date creation;

    @ManyToOne
    @JsonIgnoreProperties({"items"})
    private Utilisateur utilisateur;

    @Transient
    private String description;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getCreation() {
        return creation;
    }

    public void setCreation(Date creation) {
        this.creation = creation;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;

        Item item = (Item) o;

        if (nom != null ? !nom.equals(item.nom) : item.nom != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return nom != null ? nom.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Item{" +
                "nom='" + nom + '\'' +
                '}';
    }
}
