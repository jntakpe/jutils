package com.github.jntakpe.fmk.util.constant;

/**
 * Enumération des paramètres obligatoires
 *
 * @author jntakpe
 */
public enum MandatoryParams {

    SMTP_HOST("smtp.host"),
    SMTP_PORT("smtp.port"),
    SMTP_FROM("smtp.from"),
    SOPRA_MAIL_FRAME("sopra.mail.frame"),
    SBS_MAIL_FRAME("sbs.mail.frame");

    private final String key;

    private MandatoryParams(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
