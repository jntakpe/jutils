package com.github.jntakpe.jutils.domain;

import com.github.jntakpe.fmk.domain.GenericDomain;
import com.github.jntakpe.jutils.util.constants.ModeDegustation;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Entité représentant un café
 *
 * @author jntakpe
 */
@Entity
@SequenceGenerator(name = "SG", sequenceName = "SEQ_CAFE")
public class Cafe extends GenericDomain {

    @Column(unique = true, nullable = false)
    private String nom;

    @Column(nullable = false)
    private String origine;

    @Column(nullable = false)
    private Integer intensite;

    @Column(nullable = false)
    private Float prix;

    @Column(nullable = false)
    private String torrefaction;

    @Column(nullable = false)
    private String notesAromatiques;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ModeDegustation modeDegustation;

    @OneToMany(mappedBy = "demandeCafeId.cafe", cascade = CascadeType.REMOVE)
    private Set<DemandeCafe> demandeCafes = new HashSet<>();

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getOrigine() {
        return origine;
    }

    public void setOrigine(String origine) {
        this.origine = origine;
    }

    public Integer getIntensite() {
        return intensite;
    }

    public void setIntensite(Integer intensite) {
        this.intensite = intensite;
    }

    public Float getPrix() {
        return prix;
    }

    public void setPrix(Float prix) {
        this.prix = prix;
    }

    public String getTorrefaction() {
        return torrefaction;
    }

    public void setTorrefaction(String torrefaction) {
        this.torrefaction = torrefaction;
    }

    public String getNotesAromatiques() {
        return notesAromatiques;
    }

    public void setNotesAromatiques(String notesAromatiques) {
        this.notesAromatiques = notesAromatiques;
    }

    public ModeDegustation getModeDegustation() {
        return modeDegustation;
    }

    public void setModeDegustation(ModeDegustation modeDegustation) {
        this.modeDegustation = modeDegustation;
    }

    public Set<DemandeCafe> getDemandeCafes() {
        return demandeCafes;
    }

    public void setDemandeCafes(Set<DemandeCafe> demandeCafes) {
        this.demandeCafes = demandeCafes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cafe)) return false;

        Cafe cafe = (Cafe) o;

        if (nom != null ? !nom.equals(cafe.nom) : cafe.nom != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return nom != null ? nom.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Cafe{" +
                "nom='" + nom + '\'' +
                '}';
    }
}
