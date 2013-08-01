package com.github.jntakpe.fmk.util;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

/**
 * Classe contenant des méthodes utilitaires
 *
 * @author jntakpe
 */
public final class FmkUtils {

    public static final String CONTEXT_ROOT = "/jutils";

    public static final String PORTAL_VIEW = CONTEXT_ROOT + "/portal";

    private FmkUtils() {
    }

    /**
     * Récupère le login de l'utilisateur courant
     *
     * @return login courant
     */
    public static String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    /**
     * Rôles de l'utilisateur courant
     *
     * @return rôles
     */
    public static Collection<? extends GrantedAuthority> getCurrentRoles() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    }
}
