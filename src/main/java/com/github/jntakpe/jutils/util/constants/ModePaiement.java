package com.github.jntakpe.jutils.util.constants;

/**
 * Enumération des modes de paiement
 *
 * @author jntakpe
 */
public enum ModePaiement {

    CHEQUE("chèque"),
    VIREMENT("virement"),
    LIQUIDE_APPOINT("liquide avec appoint"),
    LIQUIDE("liquide");

    private final String description;

    private ModePaiement(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
