package com.github.jntakpe.fmk.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Classe à étendre pour la création d'entités.
 * Toute classe fille doit absolument implémenter son propre générateur de séquence.
 *
 * @author jntakpe
 */
@MappedSuperclass
public abstract class GenericDomain implements Serializable {

    /**
     * Clé primaire et technique de toutes les entités étendant cette classe
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SG")
    private Long id;

    /**
     * Version permettant de détecter les modifications concurrentes
     */
    @Version
    private Integer version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
