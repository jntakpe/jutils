package com.github.jntakpe.jutils.domain;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Clé composite de la table DemandeCafe
 *
 * @author jntakpe
 */
@Embeddable
public class DemandeCafeId implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    private Demande demande;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cafe cafe;

    public Demande getDemande() {
        return demande;
    }

    public void setDemande(Demande demande) {
        this.demande = demande;
    }

    public Cafe getCafe() {
        return cafe;
    }

    public void setCafe(Cafe cafe) {
        this.cafe = cafe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DemandeCafeId)) return false;

        DemandeCafeId that = (DemandeCafeId) o;

        if (cafe != null ? !cafe.equals(that.cafe) : that.cafe != null) return false;
        if (demande != null ? !demande.equals(that.demande) : that.demande != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = demande != null ? demande.hashCode() : 0;
        result = 31 * result + (cafe != null ? cafe.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DemandeCafeId{" +
                "demande=" + demande +
                ", cafe=" + cafe +
                '}';
    }
}
