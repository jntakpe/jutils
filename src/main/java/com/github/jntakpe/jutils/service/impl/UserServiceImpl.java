package com.github.jntakpe.jutils.service.impl;

import com.github.jntakpe.fmk.domain.Parameter;
import com.github.jntakpe.fmk.exception.BusinessCode;
import com.github.jntakpe.fmk.exception.BusinessException;
import com.github.jntakpe.fmk.service.ParameterService;
import com.github.jntakpe.fmk.service.impl.GenericServiceImpl;
import com.github.jntakpe.fmk.util.constant.MandatoryParams;
import com.github.jntakpe.fmk.util.constant.Role;
import com.github.jntakpe.jutils.domain.User;
import com.github.jntakpe.jutils.repository.UserRepository;
import com.github.jntakpe.jutils.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.joda.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

/**
 * Implémentation des services associés à l'entité {@link User}
 *
 * @author jntakpe
 * @see GenericServiceImpl
 */
@Service
public class UserServiceImpl extends GenericServiceImpl<User> implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ParameterService parameterService;

    @Override
    public CrudRepository<User, Long> getRepository() {
        return userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = findByLogin(login);
        if (user == null)
            throw new UsernameNotFoundException("Impossible de retrouver l'utilisateur '" + login + "'.");
        user.setLastAccess(Calendar.getInstance().getTime());
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), authorities);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByLogin(String login) {
        return userRepository.findByLoginIgnoreCase(login);
    }

    @Override
    @Transactional
    public User save(User user) {
        user.setFirstName(WordUtils.capitalize(user.getFirstName()));
        user.setLastName(user.getLastName().toUpperCase());
        user.setEmail(user.getEmail().toLowerCase());
        if (user.getId() == null) {
            user.setLastAccess(Instant.now().toDate());
        } else if (StringUtils.isBlank(user.getPassword())) {
            user.setPassword(findOne(user.getId()).getPassword());
        }
        if (user.getRole() == null)
            user.setRole(Role.ROLE_USER);
        return super.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean sendUserInfo(String email, String login) {
        User user = null;
        if (!StringUtils.isBlank(email)) {
            user = userRepository.findByEmail(email);
        } else if (!StringUtils.isBlank(login)) {
            user = userRepository.findByLoginIgnoreCase(login);
        }
        if (user == null)
            return false;
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

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setFrom(smtpFrom.getValue());
        mailMessage.setSubject("Mot de passe jUtils");
        mailMessage.setText("Le mot de passe du compte : " + user.getLogin() + " est : " + user.getPassword());

        mailSender.send(mailMessage);
        return true;
    }
}
