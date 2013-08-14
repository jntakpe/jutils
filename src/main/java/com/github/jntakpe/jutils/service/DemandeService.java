package com.github.jntakpe.jutils.service;

import com.github.jntakpe.fmk.service.GenericService;
import com.github.jntakpe.jutils.domain.Cafe;
import com.github.jntakpe.jutils.domain.Demande;
import com.github.jntakpe.jutils.util.dto.DemandeDTO;

import java.math.BigDecimal;
import java.util.List;

/**
 * Services associés à l'entité {@link Demande}
 *
 * @author jntakpe
 */
public interface DemandeService extends GenericService<Demande> {

    /**
     * Récupère la demande si elle existe et la liste triée des cafés
     *
     * @param id : identifiant de la demande si null recherche la demande à l'aide de l'utilisateur courant
     * @return un objet contenant la demande courante et les cafés
     */
    DemandeDTO findDemandeAndCafes(Long id);

    /**
     * Sauvegarde une demande et les cafés asociés
     *
     * @param demandeDTO objet contenant la demande et les cafés
     * @return la demande persistée
     */
    Demande saveDemandeAndCafes(DemandeDTO demandeDTO);

    /**
     * Récupère la demande actuellement de l'utilisateur
     *
     * @return la demande actuelle
     */
    Demande findByUtilisateur();

    /**
     * Met à jour le paiement d'une demande
     *
     * @param id       identifiant de la demande
     * @param paiement montant payé
     */
    void pay(Long id, BigDecimal paiement);

    /**
     * Récupère les totaux par cafés
     *
     * @return la liste des cafés avec les totaux
     */
    List<Cafe> findTotal();
}
