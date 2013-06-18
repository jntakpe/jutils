package com.github.jntakpe.fmk.web;

import com.github.jntakpe.fmk.exception.*;
import com.github.jntakpe.fmk.util.dto.ResponseMessage;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.stereotype.Component;

/**
 * Capte les exceptions sortant des méthodes AJAX de la couche web et renvoi un message adapté
 *
 * @author jntakpe
 */
@Aspect
@Component
public class WebExceptionTranslator {

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
                if (e instanceof BusinessException) {
                    ErrorCode errorCode = ((FrameworkException) e).getErrorCode();
                    Object[] params = ((FrameworkException) e).getParams();
                    String msg = MessageFormatter.arrayFormat(errorCode.getMessage(), params).getMessage();;
                    if (e instanceof TechException) {
                        return ResponseMessage.getErrorMessage(msg, e.getStackTrace());
                    } else {
                        return ResponseMessage.getErrorMessage(msg);
                    }
                } else {
                    return ResponseMessage.getErrorMessage(BusinessCode.DEFAULT_EXCEPTION.getMessage(),
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
