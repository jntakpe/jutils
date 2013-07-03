package com.github.jntakpe.jutils.web;

import com.github.jntakpe.jutils.domain.Utilisateur;
import com.github.jntakpe.jutils.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Contrôlleur gérant les écrans relatifs à l'entité {@link Utilisateur}
 *
 * @author jntakpe
 */
@Controller
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @RequestMapping(value = "/utilisateur", method = RequestMethod.GET)
    public String display() {
        return "utilisateur_list";
    }

    @RequestMapping(value = "/utilisateur/list", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<Utilisateur> list() {
        return utilisateurService.findAllUtilisateursWithItems();
    }

    @RequestMapping(value = "/utilisateur/account", method = RequestMethod.GET)
    public ModelAndView accountInfos() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ModelAndView("utilisateur_form").addObject(utilisateurService.findByLoginAndInitialize(login));
    }

}
