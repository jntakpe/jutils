package com.github.jntakpe.jutils.web;

import com.github.jntakpe.fmk.service.MessageManager;
import com.github.jntakpe.fmk.util.FmkUtils;
import com.github.jntakpe.fmk.util.constant.LogLevel;
import com.github.jntakpe.fmk.util.dto.ResponseMessage;
import com.github.jntakpe.jutils.domain.Commande;
import com.github.jntakpe.jutils.domain.Rib;
import com.github.jntakpe.jutils.service.CommandeService;
import com.github.jntakpe.jutils.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    @PreAuthorize("hasAnyRole('RESP_CAFE', 'ROLE_ADMIN')")
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView displayForm() {
        ModelAndView mv = new ModelAndView("commande_form");
        Commande commande = commandeService.findOpenCmd();
        Rib rib = utilisateurService.getCurrent().getRib();
        mv.addObject(rib == null ? new Rib() : rib);
        if (commande != null) return mv.addObject(commande);
        else return mv.addObject(new Commande());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView save(@ModelAttribute Commande commande, @ModelAttribute Rib rib, RedirectAttributes redirAttr) {
        commandeService.create(commande, rib);
        messageManager.logMessage("MSG40000", LogLevel.INFO, FmkUtils.getCurrentUsername(), commande.getCloture());
        redirAttr.addFlashAttribute(ResponseMessage.getSuccessMessage(messageManager.getMessage("commande.create")));
        //TODO redirect vers la commande
        return new ModelAndView(new RedirectView("portal"));
    }

    @InitBinder
    public void dateBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true));
    }
}
