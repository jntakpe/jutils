package com.github.jntakpe.fmk.util;

import org.springframework.security.core.context.SecurityContextHolder;

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

    public static String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
