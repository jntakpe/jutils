package com.github.jntakpe.jutils.service;

import com.github.jntakpe.fmk.service.GenericService;
import com.github.jntakpe.jutils.domain.Demande;
import com.github.jntakpe.jutils.util.dto.DemandeDTO;

/**
 * Services associés à l'entité {@link Demande}
 *
 * @author jntakpe
 */
public interface DemandeService extends GenericService<Demande> {

    /**
     * Récupère la demande si elle existe et la liste triée des cafés
     *
     * @return un objet contenant la demande courante et les cafés
     */
    DemandeDTO findDemandeAndCafes();

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

}
