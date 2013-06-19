package com.github.jntakpe.jutils.domain;

import com.github.jntakpe.fmk.domain.GenericDomain;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import java.util.Date;

/**
 * Entité représentant une commande de café
 *
 * @author jntakpe
 */
@Entity
@SequenceGenerator(name = "SG", sequenceName = "SEQ_ORDER")
public class Commande extends GenericDomain {

    private Date opening;

    private Date closing;

    @OneToOne
    private Utilisateur creator;

    private Integer totalAmount;

    private Integer paidAmount;

    private Integer totalCaps;



}
