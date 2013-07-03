package com.github.jntakpe.jutils.service.impl;

import com.github.jntakpe.fmk.domain.Parameter;
import com.github.jntakpe.fmk.exception.BusinessCode;
import com.github.jntakpe.fmk.exception.BusinessException;
import com.github.jntakpe.fmk.service.ParameterService;
import com.github.jntakpe.fmk.util.constant.MandatoryParams;
import com.github.jntakpe.jutils.domain.Utilisateur;
import com.github.jntakpe.jutils.service.MailService;
import com.github.jntakpe.jutils.service.UtilisateurService;
import com.github.jntakpe.jutils.util.dto.MailDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Implémentation de la gestion des services associés à l'envoi de mail
 *
 * @author jntakpe
 */
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private ParameterService parameterService;

    @Autowired
    private UtilisateurService utilisateurService;

    /**
     * @{inhericDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public void send(MailDTO mailDTO) throws MessagingException {
        boolean isSopra = mailDTO.getFromSopra() != null;
        Parameter smtpHost = parameterService.findByKey(MandatoryParams.SMTP_HOST.getKey());
        if (smtpHost == null || StringUtils.isBlank(smtpHost.getValue()))
            throw new BusinessException(BusinessCode.EMAIL_MISSING_PARAM, MandatoryParams.SMTP_HOST.getKey());
        Parameter smtpPort = parameterService.findByKey(MandatoryParams.SMTP_PORT.getKey());
        if (smtpPort == null || StringUtils.isBlank(smtpPort.getValue()))
            throw new BusinessException(BusinessCode.EMAIL_MISSING_PARAM, MandatoryParams.SMTP_PORT.getKey());
        Parameter smtpFrom = parameterService.findByKey(MandatoryParams.SMTP_FROM.getKey());
        if (smtpFrom == null || StringUtils.isBlank(smtpFrom.getValue()))
            throw new BusinessException(BusinessCode.EMAIL_MISSING_PARAM, MandatoryParams.SMTP_FROM.getKey());
        Parameter mailFrame = parameterService.findByKey(MandatoryParams.SOPRA_MAIL_FRAME.getKey());
        if (mailFrame == null || StringUtils.isBlank(mailFrame.getValue()))
            throw new BusinessException(BusinessCode.EMAIL_MISSING_PARAM, MandatoryParams.SOPRA_MAIL_FRAME.getKey());

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(smtpHost.getValue());
        mailSender.setPort(Integer.parseInt(smtpPort.getValue()));

        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(isSopra ? mailDTO.getFromSopra() : mailDTO.getFromOther());
        helper.setTo(mailDTO.getTo());
        helper.setSubject(mailDTO.getSubject());
        helper.setText(sopraMailBuilder(mailDTO.getFromSopra(), mailFrame.getValue(), mailDTO.getBody()));

        mailSender.send(message);
    }

    /**
     * Récupère le cadre du mail et l'initialise correctement
     *
     * @param fromMail  émetteur du mail
     * @param mailFrame cadre complet non initialisé
     * @param text      texte du mail
     * @return mailFrame initialisée
     */
    @Transactional(readOnly = true)
    private String sopraMailBuilder(String fromMail, String mailFrame, String text) {
        Utilisateur from = utilisateurService.findByMail(fromMail);
        String tel;
        String partialTel = from.getTelephone();
        if (partialTel.matches("[0-9]{5}")) {
            tel = partialTel.substring(0, 2) + " " + partialTel.substring(2, 4);
        } else {
            tel = "70 70";
        }
        mailFrame = StringUtils.replace(mailFrame, "${text}", text);
        mailFrame = StringUtils.replace(mailFrame, "${nom}", from.getNom());
        mailFrame = StringUtils.replace(mailFrame, "${email}", fromMail);
        return StringUtils.replace(mailFrame, "${tel}", tel);
    }
}
