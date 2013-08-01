package com.github.jntakpe.jutils.web;

import com.github.jntakpe.fmk.service.MessageManager;
import com.github.jntakpe.fmk.util.FmkUtils;
import com.github.jntakpe.fmk.util.constant.LogLevel;
import com.github.jntakpe.fmk.util.dto.ResponseMessage;
import com.github.jntakpe.jutils.domain.Commande;
import com.github.jntakpe.jutils.domain.Demande;
import com.github.jntakpe.jutils.domain.Rib;
import com.github.jntakpe.jutils.service.CommandeService;
import com.github.jntakpe.jutils.service.DemandeService;
import com.github.jntakpe.jutils.service.RoleService;
import com.github.jntakpe.jutils.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

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
    private DemandeService demandeService;

    @Autowired
    private RoleService roleService;

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
        return new ModelAndView(new RedirectView(FmkUtils.CONTEXT_ROOT + "/commande"));
    }

    @RequestMapping(value = "/cloture/{id}", method = RequestMethod.GET)
    public ModelAndView cloture(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Commande commande = commandeService.cloture(id);
        messageManager.logMessage("MSG40001", LogLevel.INFO, FmkUtils.getCurrentUsername(), commandeService);
        redirectAttributes.addFlashAttribute(ResponseMessage.getSuccessMessage(messageManager.getMessage("commande.close", commande)));
        return new ModelAndView(new RedirectView(FmkUtils.CONTEXT_ROOT + "/commande"));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView display(RedirectAttributes redirectAttributes) {
        if (commandeService.findOpenCmd() != null) {
            ModelAndView mv = new ModelAndView("commande_list");
            boolean isAuthored = false;
            for (GrantedAuthority role : FmkUtils.getCurrentRoles()) {
                if (role.getAuthority().equals("ROLE_ADMIN") || role.getAuthority().equals("RESP_CAFE")) {
                    isAuthored = true;
                    break;
                }
            }
            Demande demande = demandeService.findByUtilisateur();
            if (demande != null) mv.addObject("demandeId", demande.getId());
            return mv.addObject("isAuthored", isAuthored);
        } else {
            redirectAttributes.addFlashAttribute(ResponseMessage.getErrorMessage(messageManager.getMessage("zero.commande")));
            return new ModelAndView(new RedirectView(FmkUtils.PORTAL_VIEW));
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Set<Demande> list() {
        return commandeService.findDemandes();
    }

    @InitBinder
    public void dateBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true));
    }
}
