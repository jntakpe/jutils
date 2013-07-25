package com.github.jntakpe.jutils.util.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.jntakpe.jutils.domain.Cafe;
import com.github.jntakpe.jutils.domain.Demande;

import java.util.List;

/**
 * Objet permettant d'initialiser l'écran des demandes de cafés
 */
public class DemandeDTO {

    @JsonIgnoreProperties({"image"})
    private List<Cafe> cafes;

    private Demande demande;

    public List<Cafe> getCafes() {
        return cafes;
    }

    public void setCafes(List<Cafe> cafes) {
        this.cafes = cafes;
    }

    public Demande getDemande() {
        return demande;
    }

    public void setDemande(Demande demande) {
        this.demande = demande;
    }
}
