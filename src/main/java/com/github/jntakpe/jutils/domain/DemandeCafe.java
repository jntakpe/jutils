package com.github.jntakpe.jutils.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Table de jointure représentant la demande d'un café pour un utilisateur
 *
 * @author jntakpe
 */
@Entity
@Table(name = "demande_cafe")
@AssociationOverrides({
        @AssociationOverride(name = "demandeCafeId.demande"
                , joinColumns = @JoinColumn(referencedColumnName = "id", nullable = false, updatable = false)),
        @AssociationOverride(name = "demandeCafeId.cafe"
                , joinColumns = @JoinColumn(referencedColumnName = "id", nullable = false, updatable = false))})
public class DemandeCafe implements Serializable {

    @EmbeddedId
    private DemandeCafeId demandeCafeId;

    @Column(nullable = false)
    private Integer nombreBoites;

    @Transient
    public Demande getDemande() {
        return demandeCafeId.getDemande();
    }

    @Transient
    public Cafe getCafe() {
        return demandeCafeId.getCafe();
    }

    public DemandeCafeId getDemandeCafeId() {
        return demandeCafeId;
    }

    public void setDemandeCafeId(DemandeCafeId demandeCafeId) {
        this.demandeCafeId = demandeCafeId;
    }

    public Integer getNombreBoites() {
        return nombreBoites;
    }

    public void setNombreBoites(Integer nombreBoites) {
        this.nombreBoites = nombreBoites;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DemandeCafe)) return false;

        DemandeCafe that = (DemandeCafe) o;

        if (!demandeCafeId.equals(that.demandeCafeId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return demandeCafeId.hashCode();
    }

    @Override
    public String toString() {
        return "DemandeCafe{" +
                "demandeCafeId=" + demandeCafeId +
                '}';
    }
}
