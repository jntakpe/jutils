package com.github.jntakpe.jutils.service;

import com.github.jntakpe.jutils.util.dto.MailDTO;

import javax.mail.MessagingException;

/**
 * Services associés à la gestion des mails
 *
 * @author jntakpe
 */
public interface MailService {

    /**
     * Envoi le mail
     *
     * @param mailDTO objet contenant les informations sur le mail à envoyer
     */
    void send(MailDTO mailDTO) throws MessagingException;

}
