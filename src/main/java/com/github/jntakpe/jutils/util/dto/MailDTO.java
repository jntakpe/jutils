package com.github.jntakpe.jutils.util.dto;

/**
 * Objet correspondant au formulaire de saisie d'un mail
 *
 * @author jntakpe
 */
public class MailDTO {

    private String fromSopra;

    private String fromOther;

    private String to;

    private String subject;

    private String body;

    public String getFromSopra() {
        return fromSopra;
    }

    public void setFromSopra(String fromSopra) {
        this.fromSopra = fromSopra;
    }

    public String getFromOther() {
        return fromOther;
    }

    public void setFromOther(String fromOther) {
        this.fromOther = fromOther;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
