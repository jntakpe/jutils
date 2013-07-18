package com.github.jntakpe.jutils.service;

import com.github.jntakpe.fmk.service.GenericService;
import com.github.jntakpe.jutils.domain.Cafe;

import java.util.List;

/**
 * Services associés à l'entité {@link Cafe}
 *
 * @author jntakpe
 */
public interface CafeService extends GenericService<Cafe> {

    /**
     * Classe les cafés selon leur catégorie puis les tri en fonction de leur intensité
     *
     * @return Les cafés triés selon la catégorie puis l'intensité
     */
    List<Cafe> categorize();

}
