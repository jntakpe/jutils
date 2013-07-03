package com.github.jntakpe.jutils.web;

import com.github.jntakpe.fmk.service.MessageManager;
import com.github.jntakpe.fmk.util.dto.ResponseMessage;
import com.github.jntakpe.jutils.service.MailService;
import com.github.jntakpe.jutils.service.UtilisateurService;
import com.github.jntakpe.jutils.util.dto.MailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

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

    @Autowired
    private MailService mailService;

    @Autowired
    private MessageManager messageManager;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView display() {
        ModelAndView mv = new ModelAndView("mail");
        return mv.addObject("utilisateurs", utilisateurService.findAll());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView send(@ModelAttribute MailDTO mailDTO, RedirectAttributes redirectAttributes) {
        try {
            mailService.send(mailDTO);
        } catch (Exception e) {
            e.printStackTrace();

        }

        redirectAttributes.addFlashAttribute(ResponseMessage.getSuccessMessage(messageManager.getMessage("mail.send")));
        return new ModelAndView(new RedirectView("portal"));
    }
}
