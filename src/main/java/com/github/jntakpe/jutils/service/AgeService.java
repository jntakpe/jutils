package com.github.jntakpe.jutils.service;

import com.github.jntakpe.jutils.domain.Age;

import java.util.Date;

/**
 * Services associés au calcul de l'âge
 *
 * @author jntakpe
 */
public interface AgeService {

    Age calcAge(Date birthdate);
}
