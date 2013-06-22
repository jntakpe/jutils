package com.github.jntakpe.fmk.web;

import com.github.jntakpe.fmk.exception.FrameworkException;
import com.github.jntakpe.fmk.service.MessageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;


/**
 * Classe interceptant toutes les exceptions sortant de la couche web
 *
 * @author jntakpe
 */
@ControllerAdvice
public class ExceptionController {

    @Autowired
    private MessageManager messageManager;

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleException(Exception e) {
        ModelAndView mv = new ModelAndView("/error");
        if (e instanceof FrameworkException)
            mv.addObject("fmkMessage", messageManager.getMessage(((FrameworkException) e).getErrorCode(),
                    ((FrameworkException) e).getParams()));
        return mv.addObject("exception", e);
    }
}
