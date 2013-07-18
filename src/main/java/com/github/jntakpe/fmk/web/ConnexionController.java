package com.github.jntakpe.fmk.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Contrôleur de gestion du formulaire de connexion
 *
 * @author jntakpe
 */
@Controller
public class ConnexionController {

    public static final String BASIC_ROLE = "ROLE_USER";

    /**
     * Si l'utilisateur est connecté renvoi vers le portail sinon renvoi vers la page de connexion en ajoutant un
     * message d'erreur
     *
     * @param request        requête http
     * @param authentication cause de l'échec de l'authentification
     * @return vue à afficher
     */
    @RequestMapping(value = "/connexion", method = RequestMethod.GET)
    public ModelAndView connexion(HttpServletRequest request, @RequestParam(required = false) String authentication) {
        if (request.isUserInRole(BASIC_ROLE)) return new ModelAndView("redirect:/portal");
        else return new ModelAndView("connexion").addObject("authentication", authentication);

    }

    /**
     * Affiche l'écran d'accueil
     *
     * @return nom de l'écran d'accueil
     */
    @RequestMapping(value = {"/portal", "/*"}, method = RequestMethod.GET)
    public String portal() {
        return "/portal";
    }
}
