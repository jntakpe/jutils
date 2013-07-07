package com.github.jntakpe.jutils.service;

import com.github.jntakpe.jutils.util.dto.MailDTO;

/**
 * Services associés à la gestion des mails
 *
 * @author jntakpe
 */
public interface MailService {

    /**
     * Envoi le mail
     *
     * @param mailDTO      objet contenant les informations sur le mail à envoyer
     * @param previzualize true si en mode prévisualisation
     */
    void send(MailDTO mailDTO, boolean previzualize);

}
