package com.github.jntakpe.jutils.service;

import com.github.jntakpe.fmk.service.GenericService;
import com.github.jntakpe.jutils.domain.User;

/**
 * Services associés à l'entité {@link User}
 *
 * @author jntakpe
 * @see GenericService
 */
public interface UserService extends GenericService<User> {

    User findByLogin(String login);

    boolean sendUserInfo(String email, String login);

}
