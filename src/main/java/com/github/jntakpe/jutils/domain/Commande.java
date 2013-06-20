package com.github.jntakpe.jutils.domain;

import com.github.jntakpe.fmk.domain.GenericDomain;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Entité représentant la commande de café totale
 *
 * @author jntakpe
 */
@Entity
@SequenceGenerator(name="SG", sequenceName = "SEQ_COMMANDE")
public class Commande extends GenericDomain {

    private Date ouverture;

    private Date fermeture;

    private Float montantTotal;

    private Float montantPaye;

    private Integer nombreBoites;

    @OneToOne(optional = false)
    private Utilisateur responsable;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Demande> demandes = new HashSet<>();

}
