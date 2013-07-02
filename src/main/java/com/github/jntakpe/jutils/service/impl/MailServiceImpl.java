package com.github.jntakpe.jutils.service.impl;

import com.github.jntakpe.fmk.domain.Parameter;
import com.github.jntakpe.fmk.exception.BusinessCode;
import com.github.jntakpe.fmk.exception.BusinessException;
import com.github.jntakpe.fmk.service.ParameterService;
import com.github.jntakpe.fmk.util.constant.MandatoryParams;
import com.github.jntakpe.jutils.service.MailService;
import com.github.jntakpe.jutils.util.dto.MailDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implémentation de la gestion des services associés à l'envoi de mail
 *
 * @author jntakpe
 */
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private ParameterService parameterService;

    /**
     * @{inhericDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public void send(MailDTO mailDTO) {
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

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(smtpHost.getValue());
        mailSender.setPort(Integer.parseInt(smtpPort.getValue()));

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(isSopra ? mailDTO.getFromSopra() : mailDTO.getFromOther());
        message.setTo(mailDTO.getTo());
        message.setSubject(mailDTO.getSubject());
        message.setText(mailDTO.getBody());

        mailSender.send(message);
    }
}
