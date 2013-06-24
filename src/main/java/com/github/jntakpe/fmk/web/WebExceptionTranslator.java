package com.github.jntakpe.fmk.web;

import com.github.jntakpe.fmk.exception.BusinessException;
import com.github.jntakpe.fmk.exception.ErrorCode;
import com.github.jntakpe.fmk.exception.FrameworkException;
import com.github.jntakpe.fmk.exception.TechException;
import com.github.jntakpe.fmk.service.MessageManager;
import com.github.jntakpe.fmk.util.dto.ResponseMessage;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Capte les exceptions sortant des méthodes AJAX de la couche web et renvoi un message adapté
 *
 * @author jntakpe
 */
@Aspect
@Component
public class WebExceptionTranslator {

    @Autowired
    private MessageManager messageManager;

    /**
     * Méthode interceptant les exceptions lancées par la couche web et renvoi un message adapté pour les requêtes AJAX
     *
     * @param joinPoint méthode initialement appelée (greffon)
     * @return L'objet normalement retourné par la méthode appelée
     * @throws Throwable rethrow les exceptions
     */
    @Around("execution(* com.github.jntakpe.*.web..*.*(..))" +
            "&& @annotation(org.springframework.web.bind.annotation.ResponseBody)")
    public Object catchExceptions(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            if (isReturningResponseMessage((MethodSignature) joinPoint.getSignature())) {
                if (e instanceof FrameworkException) {
                    ErrorCode errorCode = ((FrameworkException) e).getErrorCode();
                    Object[] params = ((FrameworkException) e).getParams();
                    String msg = messageManager.getMessage(errorCode, params);
                    if (e instanceof TechException) {
                        return ResponseMessage.getErrorMessage(msg, e.getStackTrace());
                    } else {
                        return ResponseMessage.getErrorMessage(msg);
                    }
                } else {
                    return ResponseMessage.getErrorMessage(messageManager.getMessage("unknown.error"),
                            e.getStackTrace());
                }
            }
            throw e;
        }
        return result;
    }

    /**
     * Méthode indiquant si le greffon retourne un {@link ResponseMessage}
     *
     * @return true si le type de retour est bien un {@link ResponseMessage}
     */
    private boolean isReturningResponseMessage(MethodSignature signature) {
        return signature.getReturnType().equals(ResponseMessage.class);
    }
}
