package com.github.jntakpe.jutils.domain;

import javax.persistence.Embeddable;

/**
 * Rib associé à une {@link Commande}
 *
 * @author jntakpe
 */
@Embeddable
public class Rib {

    private String titulaire;

    private String iban;

    private String bic;

    public String getTitulaire() {
        return titulaire;
    }

    public void setTitulaire(String titulaire) {
        this.titulaire = titulaire;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }
}
