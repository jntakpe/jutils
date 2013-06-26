package com.github.jntakpe.jutils.web;

import com.github.jntakpe.jutils.domain.Utilisateur;
import com.github.jntakpe.jutils.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

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
        return "user_list";
    }

    @RequestMapping(value = "/utilisateur/list", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<Utilisateur> list() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Iterable<Utilisateur> utilisateurs = utilisateurService.findAll();
        stopWatch.stop();
        System.out.println("Temps : " + stopWatch.getLastTaskTimeMillis());
        return utilisateurs;
    }

}
