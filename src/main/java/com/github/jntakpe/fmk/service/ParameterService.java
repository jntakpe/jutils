package com.github.jntakpe.fmk.service;

import com.github.jntakpe.fmk.domain.Parameter;

/**
 * Services associés à l'entité {@link Parameter}
 *
 * @author jntakpe
 * @see GenericService
 */
public interface ParameterService extends GenericService<Parameter> {

    /**
     * Récupère un {@link Parameter} à l'aide de sa clé
     *
     * @param key clé du paramètre
     * @return le paramètre ou null si aucun paramètre ne correspond à cette clé
     */
    Parameter findByKey(String key);

}
