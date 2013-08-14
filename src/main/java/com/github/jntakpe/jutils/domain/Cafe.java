package com.github.jntakpe.jutils.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.jntakpe.fmk.domain.GenericDomain;
import com.github.jntakpe.jutils.util.constants.Categorie;
import com.github.jntakpe.jutils.util.constants.ProfilAromatique;

import javax.persistence.*;
import java.math.BigDecimal;
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
    private BigDecimal prix;

    private byte[] image;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Categorie categorie;

    @Enumerated(EnumType.STRING)
    private ProfilAromatique profilAromatique;

    @JsonIgnore
    @OneToMany(mappedBy = "demandeCafeId.cafe", cascade = CascadeType.REMOVE)
    private Set<DemandeCafe> demandeCafes = new HashSet<>();

    @Transient
    private Integer nb = 0;

    @Transient
    private Integer nbTotal = 0;

    @Transient
    private BigDecimal prixTotal = new BigDecimal(0);

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

    public BigDecimal getPrix() {
        return prix;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
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

    public Integer getNb() {
        return nb;
    }

    public void setNb(Integer nb) {
        this.nb = nb;
    }

    public Integer getNbTotal() {
        return nbTotal;
    }

    public void setNbTotal(Integer nbTotal) {
        this.nbTotal = nbTotal;
    }

    public BigDecimal getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(BigDecimal prixTotal) {
        this.prixTotal = prixTotal;
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
