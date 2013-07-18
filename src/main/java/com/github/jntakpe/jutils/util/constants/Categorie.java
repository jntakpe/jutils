package com.github.jntakpe.jutils.util.constants;

/**
 * Enumération des modes de dégustation des cafés
 *
 * @author jntakpe
 */
public enum Categorie {

    ESPRESSO("Espresso"),
    LUNGO("Lungo"),
    DECAFFEINATO("Decaffeinato"),
    VARIATIONS("Variations");

    private final String text;

    private Categorie(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
