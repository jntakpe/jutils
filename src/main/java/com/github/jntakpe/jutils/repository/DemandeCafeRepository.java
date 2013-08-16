package com.github.jntakpe.jutils.repository;

import com.github.jntakpe.jutils.domain.*;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Interface définissant les méthodes de manipulation de l'entité {@link DemandeCafe}
 *
 * @author jntakpe
 */
public interface DemandeCafeRepository extends CrudRepository<DemandeCafe, DemandeCafeId> {

    @Modifying
    @Query("DELETE FROM DemandeCafe dc WHERE dc.demandeCafeId.demande = :demande")
    void deleteByDemande(@Param("demande") Demande demande);

    @Modifying
    @Query("SELECT dc FROM DemandeCafe dc WHERE dc.demandeCafeId.cafe = :cafe AND dc.demandeCafeId.demande.commande = :commande")
    List<DemandeCafe> findDemandeCafesByCommandeAndCafe(@Param("cafe") Cafe cafe, @Param("commande") Commande commande);
}
