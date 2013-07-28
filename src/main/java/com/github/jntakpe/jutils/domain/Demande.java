package com.github.jntakpe.jutils.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.jntakpe.fmk.domain.GenericDomain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Entité représentant la demande en café d'un {@link Utilisateur}
 *
 * @author jntakpe
 */
@Entity
@SequenceGenerator(name = "SG", sequenceName = "SEQ_COMMANDE_UTILISATEUR")
public class Demande extends GenericDomain {

    @Column(nullable = false)
    private BigDecimal montantTotal = new BigDecimal(0);

    @Column(nullable = false)
    private BigDecimal montantPaye = new BigDecimal(0);

    @Column(nullable = false)
    private Integer nombreBoites = 0;

    @ManyToOne(optional = false)
    @JsonIgnore
    private Commande commande;

    @ManyToOne(optional = false)
    @JsonIgnore
    private Utilisateur utilisateur;

    @OneToMany(mappedBy = "demandeCafeId.demande", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<DemandeCafe> demandeCafes = new HashSet<>();

    public BigDecimal getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(BigDecimal montantTotal) {
        this.montantTotal = montantTotal;
    }

    public BigDecimal getMontantPaye() {
        return montantPaye;
    }

    public void setMontantPaye(BigDecimal montantPaye) {
        this.montantPaye = montantPaye;
    }

    public Integer getNombreBoites() {
        return nombreBoites;
    }

    public void setNombreBoites(Integer nombreBoites) {
        this.nombreBoites = nombreBoites;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
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
        if (!(o instanceof Demande)) return false;

        Demande demande = (Demande) o;

        if (commande != null ? !commande.equals(demande.commande) : demande.commande != null) return false;
        if (utilisateur != null ? !utilisateur.equals(demande.utilisateur) : demande.utilisateur != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = commande != null ? commande.hashCode() : 0;
        result = 31 * result + (utilisateur != null ? utilisateur.hashCode() : 0);
        return result;
    }
}
