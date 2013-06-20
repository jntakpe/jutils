package com.github.jntakpe.jutils.domain;

import com.github.jntakpe.fmk.domain.GenericDomain;
import com.github.jntakpe.jutils.util.constants.ModeDegustation;
import com.github.jntakpe.jutils.util.constants.ProfilAromatique;

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
    private String description;

    @Column(nullable = false)
    private Integer intensite;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ModeDegustation modeDegustation;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProfilAromatique profilAromatique;

    @OneToMany(mappedBy = "demandeCafeId.cafe", cascade = CascadeType.REMOVE)
    private Set<DemandeCafe> demandeCafes = new HashSet<>();

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIntensite() {
        return intensite;
    }

    public void setIntensite(Integer intensite) {
        this.intensite = intensite;
    }

    public ModeDegustation getModeDegustation() {
        return modeDegustation;
    }

    public void setModeDegustation(ModeDegustation modeDegustation) {
        this.modeDegustation = modeDegustation;
    }

    public ProfilAromatique getProfilAromatique() {
        return profilAromatique;
    }

    public void setProfilAromatique(ProfilAromatique profilAromatique) {
        this.profilAromatique = profilAromatique;
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

        if (!nom.equals(cafe.nom)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return nom.hashCode();
    }

    @Override
    public String toString() {
        return "Cafe{" +
                "nom='" + nom + '\'' +
                '}';
    }
}
