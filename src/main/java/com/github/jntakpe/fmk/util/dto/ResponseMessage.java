package com.github.jntakpe.fmk.util.dto;

/**
 * Réponse renvoyée par l'application aux requêtes notamment aux requêtes AJAX.
 * Peut aussi être ajouté à un objet ModelAndView.
 *
 * @author jntakpe
 * @author cegiraud
 */
public class ResponseMessage {

    /**
     * Message qui sera affiché à l'écran
     */
    private String message;

    /**
     * Indicateur permettant de savoir si le message doit être affiché dans une alerte d'erreur
     */
    private boolean success;

    /**
     * Objet permettant de passer n'importe quel paramètre additionnel
     */
    private Object data;

    private ResponseMessage() {
    }

    private ResponseMessage(boolean success) {
        this.success = success;
    }

    private ResponseMessage(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    private ResponseMessage(boolean success, Object data) {
        this.success = success;
        this.data = data;
    }

    private ResponseMessage(String message, boolean success, Object data) {
        this.message = message;
        this.success = success;
        this.data = data;
    }

    public static ResponseMessage getSuccessMessage() {
        return new ResponseMessage(true);
    }

    public static ResponseMessage getSuccessMessage(String message) {
        return new ResponseMessage(message, true);
    }

    public static ResponseMessage getSuccessMessage(Object data) {
        return new ResponseMessage(true, data);
    }

    public static ResponseMessage getSuccessMessage(String message, Object data) {
        return new ResponseMessage(message, true, data);
    }

    public static ResponseMessage getErrorMessage(String message) {
        return new ResponseMessage("Erreur. " + message, false);
    }

    public static ResponseMessage getErrorMessage(String message, Object data) {
        return new ResponseMessage("Erreur. " + message, false, data);
    }

    public final String getMessage() {
        return message;
    }

    public final boolean isSuccess() {
        return success;
    }

    public final Object getData() {
        return data;
    }

    public final void setData(Object data) {
        this.data = data;
    }
}
