package com.github.jntakpe.jutils.web;

import com.github.jntakpe.fmk.service.MessageManager;
import com.github.jntakpe.fmk.util.FmkUtils;
import com.github.jntakpe.fmk.util.constant.LogLevel;
import com.github.jntakpe.fmk.util.dto.ResponseMessage;
import com.github.jntakpe.jutils.domain.Commande;
import com.github.jntakpe.jutils.domain.Demande;
import com.github.jntakpe.jutils.service.CommandeService;
import com.github.jntakpe.jutils.service.DemandeService;
import com.github.jntakpe.jutils.util.dto.DemandeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Contrôleur gérant les écrans relatifs à une demande de café
 *
 * @author jntakpe
 */
@Controller
@RequestMapping("/demande")
public class DemandeController {

    @Autowired
    private DemandeService demandeService;

    @Autowired
    private CommandeService commandeService;

    @Autowired
    private MessageManager messageManager;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView display(RedirectAttributes redirectAttributes) {
        ModelAndView mv;
        Commande commande = commandeService.findOpenCmd();
        if (commande != null) mv = new ModelAndView("demande");
        else {
            redirectAttributes.addFlashAttribute(ResponseMessage.getErrorMessage(messageManager.getMessage("zero.commande")));
            mv = new ModelAndView(new RedirectView(FmkUtils.PORTAL_VIEW));
        }
        return mv;
    }

    @RequestMapping(value = "/cafes", method = RequestMethod.GET)
    @ResponseBody
    public DemandeDTO initialize() {
        return demandeService.findDemandeAndCafes();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage save(@RequestBody DemandeDTO demandeDTO) {
        Demande demande = demandeService.saveDemandeAndCafes(demandeDTO);
        messageManager.logMessage("MSG50000", LogLevel.INFO, FmkUtils.getCurrentUsername(), demandeDTO.getDemande().getNombreBoites());
        String redirUrl = FmkUtils.CONTEXT_ROOT + "/commande";
        return ResponseMessage.getSuccessMessage(messageManager.getMessage("demande.saved"), redirUrl);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseMessage delete(@PathVariable Long id) {
        Demande demande = demandeService.findOne(id);
        String user = demande.getUtilisateur().getLogin();
        demandeService.delete(id);
        messageManager.logMessage("MSG50001", LogLevel.INFO, FmkUtils.getCurrentUsername(), user);
        return ResponseMessage.getSuccessMessage(messageManager.getMessage("demande.deleted"), FmkUtils.CONTEXT_ROOT + "/commande");
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteCurrent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        demandeService.delete(id);
        messageManager.logMessage("MSG50002", LogLevel.INFO, FmkUtils.getCurrentUsername());
        redirectAttributes.addFlashAttribute(ResponseMessage.getSuccessMessage(messageManager.getMessage("demande.deleted")));
        return new ModelAndView(new RedirectView(FmkUtils.CONTEXT_ROOT + "/commande"));
    }

    @RequestMapping(value = "/popup/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage popupPaiement(@PathVariable Long id) {
        return ResponseMessage.getSuccessMessage(demandeService.findOne(id));
    }

    @RequestMapping(value = "/paiement", method = RequestMethod.POST)
    public ModelAndView paiement(@ModelAttribute Demande demande, RedirectAttributes redirectAttributes) {
        demandeService.pay(demande.getId(), demande.getMontantPaye());
        redirectAttributes.addFlashAttribute(ResponseMessage.getSuccessMessage(messageManager.getMessage("demande.paiement")));
        return new ModelAndView(new RedirectView(FmkUtils.CONTEXT_ROOT + "/commande"));
    }
}
