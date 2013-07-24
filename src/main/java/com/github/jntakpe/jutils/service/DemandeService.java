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
     * @param demandeId identifiant de la demande
     * @return un objet contenant la demande courante et les cafés
     */
    DemandeDTO findDemandeAndCafes(Long demandeId);

}
