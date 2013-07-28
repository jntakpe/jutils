package com.github.jntakpe.jutils.repository;

import com.github.jntakpe.jutils.domain.Commande;
import com.github.jntakpe.jutils.domain.Demande;
import com.github.jntakpe.jutils.domain.Utilisateur;
import org.springframework.data.repository.CrudRepository;

/**
 * Interface définissant les méthodes de manipulation de l'entité {@link Demande}
 *
 * @author jntakpe
 */
public interface DemandeRepository extends CrudRepository<Demande, Long> {

    /**
     * Récupère une demande en fonction d'une commande et d'un utilisateur
     *
     * @param commande    commande mère de la demande
     * @param utilisateur utilisateur ayant fait la demande
     * @return la demande recherchée
     */
    Demande findByCommandeAndUtilisateur(Commande commande, Utilisateur utilisateur);
}
