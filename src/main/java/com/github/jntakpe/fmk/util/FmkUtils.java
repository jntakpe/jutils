package com.github.jntakpe.fmk.util;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Classe contenant des méthodes utilitaires
 *
 * @author jntakpe
 */
public final class FmkUtils {

    private FmkUtils() {
    }

    public static String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
