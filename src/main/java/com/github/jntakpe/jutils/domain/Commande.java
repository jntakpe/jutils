package com.github.jntakpe.jutils.domain;

import com.github.jntakpe.fmk.domain.GenericDomain;
import org.joda.time.Instant;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Entité représentant la commande de café totale
 *
 * @author jntakpe
 */
@Entity
@SequenceGenerator(name = "SG", sequenceName = "SEQ_COMMANDE")
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"ouverture", "fermeture"})})
public class Commande extends GenericDomain {

    @Column(nullable = false)
    private Date ouverture = Instant.now().toDate();

    @Column(nullable = false)
    private Date fermeture;

    private Float montantTotal;

    private Float montantPaye;

    private Integer nombreBoites;

    private String informations;

    @OneToOne(optional = false)
    private Utilisateur responsable;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Demande> demandes = new HashSet<>();

    public Date getOuverture() {
        return ouverture;
    }

    public void setOuverture(Date ouverture) {
        this.ouverture = ouverture;
    }

    public Date getFermeture() {
        return fermeture;
    }

    public void setFermeture(Date fermeture) {
        this.fermeture = fermeture;
    }

    public Float getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(Float montantTotal) {
        this.montantTotal = montantTotal;
    }

    public Float getMontantPaye() {
        return montantPaye;
    }

    public void setMontantPaye(Float montantPaye) {
        this.montantPaye = montantPaye;
    }

    public Integer getNombreBoites() {
        return nombreBoites;
    }

    public void setNombreBoites(Integer nombreBoites) {
        this.nombreBoites = nombreBoites;
    }

    public String getInformations() {
        return informations;
    }

    public void setInformations(String informations) {
        this.informations = informations;
    }

    public Utilisateur getResponsable() {
        return responsable;
    }

    public void setResponsable(Utilisateur responsable) {
        this.responsable = responsable;
    }

    public Set<Demande> getDemandes() {
        return demandes;
    }

    public void setDemandes(Set<Demande> demandes) {
        this.demandes = demandes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Commande)) return false;

        Commande commande = (Commande) o;

        if (!fermeture.equals(commande.fermeture)) return false;
        if (!ouverture.equals(commande.ouverture)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = ouverture.hashCode();
        result = 31 * result + fermeture.hashCode();
        return result;
    }
}
