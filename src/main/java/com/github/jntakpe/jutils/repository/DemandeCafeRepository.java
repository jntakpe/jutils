package com.github.jntakpe.jutils.repository;

import com.github.jntakpe.jutils.domain.Cafe;
import com.github.jntakpe.jutils.domain.Demande;
import com.github.jntakpe.jutils.domain.DemandeCafe;
import com.github.jntakpe.jutils.domain.DemandeCafeId;
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
    @Query("SELECT dc FROM DemandeCafe dc WHERE dc.demandeCafeId.cafe = :cafe")
    List<DemandeCafe> findDemandeCafesByCafe(@Param("cafe") Cafe cafe);
}
