package com.github.jntakpe.fmk.service;

import com.github.jntakpe.fmk.exception.ErrorCode;
import com.github.jntakpe.fmk.util.constant.LogLevel;

/**
 * Récupération des messages via des différents messages bundles
 *
 * @author cegiraud
 * @author jntakpe
 */
public interface MessageManager {

    /**
     * Récupère un message d'erreur dans les bundles de l'application et remplace les variables
     *
     * @param codeMessage code du message
     * @param args        paramètres du message
     * @return le message completé
     */
    String getMessage(String codeMessage, Object... args);

    /**
     * Récupère un message d'erreur dans les bundles de l'application et remplace les variables
     *
     * @param errorCode code du message correspondant à un code erreur
     * @param args      paramètres du message
     * @return le message completé
     */
    String getMessage(ErrorCode errorCode, Object... args);

    /**
     * Log un message d'erreur récupéré dans un des bundles de l'application
     *
     * @param codeMessage code du message
     * @param logLevel    niveau de log
     * @param args        paramètres du message
     */
    void logMessage(String codeMessage, LogLevel logLevel, Object... args);

    /**
     * Log un message d'erreur récupéré dans un des bundles de l'application
     *
     * @param errorCode code du message correspondant à un code erreur
     * @param logLevel  niveau de log
     * @param args      paramètres du message
     */
    void logMessage(ErrorCode errorCode, LogLevel logLevel, Object... args);
}
