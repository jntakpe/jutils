package com.github.jntakpe.jutils.web;

import com.github.jntakpe.jutils.domain.Commande;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Contrôlleur gérant les écrans relatifs à une commande de café
 *
 * @author jntakpe
 */
@Controller
@RequestMapping("/commande")
public class CommandeController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView displayForm() {
        ModelAndView mv = new ModelAndView("commande_form");
        return mv.addObject(new Commande());
    }
}
