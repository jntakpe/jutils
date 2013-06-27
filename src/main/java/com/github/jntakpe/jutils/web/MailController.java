package com.github.jntakpe.jutils.web;

import com.github.jntakpe.jutils.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Contrôleur de l'éditeur de mail
 *
 * @author jntakpe
 */
@Controller
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private UtilisateurService utilisateurService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView display() {
        ModelAndView mv = new ModelAndView("mail");
        return mv.addObject("utilisateurs", utilisateurService.findAll());
    }
}
