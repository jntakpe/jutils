package com.github.jntakpe.fmk.service.impl;


import com.github.jntakpe.fmk.exception.TechCode;
import com.github.jntakpe.fmk.exception.TechException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

/**
 * Capte les exceptions courantes sortant de la couche service et les traduit.
 * Cette classe est éxécutée après le passages des post-processeurs Spring
 *
 * @author jntakpe
 */
@Aspect
@Component
@Order(Integer.MIN_VALUE)
public class ServiceExceptionTranslator {

    /**
     * Méthode interceptant les exceptions lancées par la couche service/business lors d'une interaction avec
     * une source de données
     *
     * @param joinPoint méthode initialement appelée (greffon)
     * @return L'objet normalement retourné par la méthode appelée
     * @throws Throwable rethrow les exceptions
     */
    @Around("execution(* com.github.jntakpe.*.service..*.*(..))")
    public Object catchExceptions(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        try {
            result = joinPoint.proceed();
        } catch (DataAccessException e) {
            e.printStackTrace();
            for (TechCode techCode : TechCode.values()) {
                if (e.getClass().isAssignableFrom(techCode.getSourceException())) {
                    throw new TechException(e.getMessage(), e.getCause(), techCode);
                }
            }
            throw e;
        }
        return result;
    }

}
