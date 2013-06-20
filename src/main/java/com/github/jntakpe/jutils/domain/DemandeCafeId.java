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

        if (!cafe.equals(that.cafe)) return false;
        if (!demande.equals(that.demande)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = demande.hashCode();
        result = 31 * result + cafe.hashCode();
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
