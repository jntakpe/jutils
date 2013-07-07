package com.github.jntakpe.jutils.web;

import com.github.jntakpe.fmk.service.MessageManager;
import com.github.jntakpe.fmk.util.dto.ResponseMessage;
import com.github.jntakpe.fmk.web.GenericController;
import com.github.jntakpe.jutils.domain.Commande;
import com.github.jntakpe.jutils.domain.Rib;
import com.github.jntakpe.jutils.domain.Utilisateur;
import com.github.jntakpe.jutils.service.CommandeService;
import com.github.jntakpe.jutils.service.UtilisateurService;
import com.github.jntakpe.jutils.util.constants.ModePaiement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Contrôlleur gérant les écrans relatifs à une commande de café
 *
 * @author jntakpe
 */
@Controller
@RequestMapping("/commande")
public class CommandeController {

    @Autowired
    private CommandeService commandeService;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private MessageManager messageManager;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView displayForm() {
        if (commandeService.isOpenCmd()) {
            String msg = messageManager.getMessage("already.open.cmd");
            //TODO Débrancher sur écran de cloture directement
            return new ModelAndView("portal").addObject(ResponseMessage.getErrorMessage(msg));
        } else {
            ModelAndView mv = new ModelAndView("commande_form");
            mv.addObject("modes", ModePaiement.values());
            Rib rib = utilisateurService.getCurrent().getRib();
            mv.addObject(rib == null ? new Rib() : rib);
            return mv.addObject(new Commande());
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView save(@ModelAttribute Commande commande) {
        return new ModelAndView("portal");
    }
}
