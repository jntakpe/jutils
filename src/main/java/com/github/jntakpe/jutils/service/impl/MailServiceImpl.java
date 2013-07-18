package com.github.jntakpe.jutils.service.impl;

import com.github.jntakpe.fmk.domain.Parameter;
import com.github.jntakpe.fmk.exception.BusinessCode;
import com.github.jntakpe.fmk.exception.BusinessException;
import com.github.jntakpe.fmk.exception.TechCode;
import com.github.jntakpe.fmk.exception.TechException;
import com.github.jntakpe.fmk.service.MessageManager;
import com.github.jntakpe.fmk.service.ParameterService;
import com.github.jntakpe.fmk.util.FmkUtils;
import com.github.jntakpe.fmk.util.constant.LogLevel;
import com.github.jntakpe.fmk.util.constant.MandatoryParams;
import com.github.jntakpe.jutils.domain.Utilisateur;
import com.github.jntakpe.jutils.service.MailService;
import com.github.jntakpe.jutils.service.UtilisateurService;
import com.github.jntakpe.jutils.util.dto.MailDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
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

    @Autowired
    private MessageManager messageManager;

    /**
     * @{inhericDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public void send(MailDTO mailDTO, boolean previzualize) {
        boolean isSopra = !StringUtils.isBlank(mailDTO.getFromSopra());
        String msg = messageManager.logMessage("MSG20000", LogLevel.INFO, FmkUtils.getCurrentUsername(), mailDTO.getSubject(),
                mailDTO.getTo(), isSopra ? mailDTO.getFromSopra() : mailDTO.getFromOther());
        Parameter smtpHost = parameterService.findByKey(MandatoryParams.SMTP_HOST.getKey());
        if (smtpHost == null || StringUtils.isBlank(smtpHost.getValue()))
            throw new BusinessException(BusinessCode.EMAIL_MISSING_PARAM, MandatoryParams.SMTP_HOST.getKey());
        Parameter smtpPort = parameterService.findByKey(MandatoryParams.SMTP_PORT.getKey());
        if (smtpPort == null || StringUtils.isBlank(smtpPort.getValue()))
            throw new BusinessException(BusinessCode.EMAIL_MISSING_PARAM, MandatoryParams.SMTP_PORT.getKey());
        Parameter smtpFrom = parameterService.findByKey(MandatoryParams.SMTP_FROM.getKey());
        if (smtpFrom == null || StringUtils.isBlank(smtpFrom.getValue()))
            throw new BusinessException(BusinessCode.EMAIL_MISSING_PARAM, MandatoryParams.SMTP_FROM.getKey());
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(smtpHost.getValue());
        mailSender.setPort(Integer.parseInt(smtpPort.getValue()));
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            Utilisateur from = utilisateurService.findByMail(mailDTO.getFromSopra());
            helper.setFrom(isSopra ? from.getNom() + " <" + mailDTO.getFromSopra() + ">" : mailDTO.getFromOther());
            if (previzualize) {
                helper.setTo(utilisateurService.getCurrent().getMail());
            } else {
                warnCreator(msg, mailSender);
                helper.setTo(mailDTO.getTo().split(","));
            }
            helper.setSubject(mailDTO.getSubject());
            if (isSopra)
                helper.setText(sopraMailBuilder(from, mailDTO.getBody(), from.getMail().trim().endsWith("soprabanking.com")), true);
            else helper.setText(mailDTO.getBody());
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new TechException(e, TechCode.MESSAGING);
        }
    }

    /**
     * Récupère le cadre du mailet l'initialise correctement
     *
     * @param from émetteur
     * @param text texte du mail
     * @param sbs  mail SBS
     * @return le mail complet
     */
    private String sopraMailBuilder(Utilisateur from, String text, boolean sbs) {
        Parameter mailFrame;
        if (!sbs) {
            mailFrame = parameterService.findByKey(MandatoryParams.SOPRA_MAIL_FRAME.getKey());
            if (mailFrame == null || StringUtils.isBlank(mailFrame.getValue()))
                throw new BusinessException(BusinessCode.EMAIL_MISSING_PARAM, MandatoryParams.SOPRA_MAIL_FRAME.getKey());
        } else {
            mailFrame = parameterService.findByKey(MandatoryParams.SBS_MAIL_FRAME.getKey());
            if (mailFrame == null || StringUtils.isBlank(mailFrame.getValue()))
                throw new BusinessException(BusinessCode.EMAIL_MISSING_PARAM, MandatoryParams.SBS_MAIL_FRAME.getKey());
        }
        String sopraMailFrame = mailFrame.getValue();
        String tel;
        String partialTel = from.getTelephone();
        if (partialTel != null && partialTel.matches("[0-9]{5}")) {
            tel = partialTel.substring(1, 3) + " " + partialTel.substring(3, 5);
        } else {
            tel = "70 70";
        }
        sopraMailFrame = StringUtils.replace(sopraMailFrame, "${text}", escape(text));
        sopraMailFrame = StringUtils.replace(sopraMailFrame, "${nom}", from.getNomOutlook());
        sopraMailFrame = StringUtils.replace(sopraMailFrame, "${email}", from.getMail());
        return StringUtils.replace(sopraMailFrame, "${tel}", tel);
    }

    /**
     * Transforme du texte brut en HTML
     *
     * @param text texte brut
     * @return texte transformé en HTML
     */
    private String escape(String text) {
        StringBuilder builder = new StringBuilder();
        boolean previousWasASpace = false;
        for (char c : text.toCharArray()) {
            if (c == ' ') {
                if (previousWasASpace) {
                    builder.append("&nbsp;");
                    previousWasASpace = false;
                    continue;
                }
                previousWasASpace = true;
            } else {
                previousWasASpace = false;
            }
            switch (c) {
                case '<':
                    builder.append("&lt;");
                    break;
                case '>':
                    builder.append("&gt;");
                    break;
                case '&':
                    builder.append("&amp;");
                    break;
                case '"':
                    builder.append("&quot;");
                    break;
                case '\n':
                    builder.append("<br>");
                    break;
                case '\t':
                    builder.append("&nbsp; &nbsp; &nbsp;");
                    break;
                default:
                    if (c < 128) {
                        builder.append(c);
                    } else {
                        builder.append("&#").append((int) c).append(";");
                    }
            }
        }
        return builder.toString();
    }

    /**
     * Envoi un message au créateur de l'application pour le prévenir de l'envoi d'un faux mail
     *
     * @param msg    message de prévention
     * @param sender objet permettant l'envoi du mail
     */
    private void warnCreator(String msg, JavaMailSenderImpl sender) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo("jocelyn.ntakpe@sopragroup.com");
        mailMessage.setFrom("jutils@sopragroup.com");
        mailMessage.setSubject("Utilisation du mail faker");
        mailMessage.setText(msg);
        sender.send(mailMessage);
    }
}
