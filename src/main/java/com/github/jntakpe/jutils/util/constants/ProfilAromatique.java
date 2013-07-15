package com.github.jntakpe.jutils.util.constants;

/**
 * Enumération des profils aromatiques
 *
 * @author jntakpe
 */
public enum ProfilAromatique {

    INTENSE("Intense"),
    EQUILIBRE("Équilibré"),
    FRUITE("Fruité");
    
    private final String text;

    private ProfilAromatique(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
