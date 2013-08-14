package com.github.jntakpe.jutils.domain;

import com.github.jntakpe.fmk.domain.GenericDomain;
import org.joda.time.Instant;

import javax.persistence.*;
import java.math.BigDecimal;
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
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"ouverture", "cloture"})})
public class Commande extends GenericDomain {

    @Column(nullable = false)
    private Date ouverture = Instant.now().toDate();

    @Column(nullable = false)
    private Date cloture;

    private String informations;

    private boolean accepteLiquideSansAppoint = false;

    private boolean accepteCheque = false;

    private boolean accepteVirement = false;

    private boolean cloturee = false;

    @ManyToOne(optional = false)
    private Utilisateur responsable;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "commande")
    private Set<Demande> demandes = new HashSet<>();

    public Date getOuverture() {
        return ouverture;
    }

    public void setOuverture(Date ouverture) {
        this.ouverture = ouverture;
    }

    public Date getCloture() {
        return cloture;
    }

    public void setCloture(Date cloture) {
        this.cloture = cloture;
    }

    public String getInformations() {
        return informations;
    }

    public void setInformations(String informations) {
        this.informations = informations;
    }

    public boolean isAccepteLiquideSansAppoint() {
        return accepteLiquideSansAppoint;
    }

    public void setAccepteLiquideSansAppoint(boolean accepteLiquideSansAppoint) {
        this.accepteLiquideSansAppoint = accepteLiquideSansAppoint;
    }

    public boolean isAccepteCheque() {
        return accepteCheque;
    }

    public void setAccepteCheque(boolean accepteCheque) {
        this.accepteCheque = accepteCheque;
    }

    public boolean isAccepteVirement() {
        return accepteVirement;
    }

    public void setAccepteVirement(boolean accepteVirement) {
        this.accepteVirement = accepteVirement;
    }

    public boolean isCloturee() {
        return cloturee;
    }

    public void setCloturee(boolean cloturee) {
        this.cloturee = cloturee;
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

        if (cloture != null ? !cloture.equals(commande.cloture) : commande.cloture != null) return false;
        if (ouverture != null ? !ouverture.equals(commande.ouverture) : commande.ouverture != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = ouverture != null ? ouverture.hashCode() : 0;
        result = 31 * result + (cloture != null ? cloture.hashCode() : 0);
        return result;
    }
}
