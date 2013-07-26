package com.github.jntakpe.jutils.web;

import com.github.jntakpe.fmk.util.dto.ResponseMessage;
import com.github.jntakpe.jutils.domain.Demande;
import com.github.jntakpe.jutils.service.DemandeService;
import com.github.jntakpe.jutils.util.dto.DemandeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @RequestMapping(method = RequestMethod.GET)
    public String display() {
        return "demande";
    }

    @RequestMapping(value = "/cafes", method = RequestMethod.GET)
    @ResponseBody
    public DemandeDTO initialize(@RequestParam(required = false) Long id) {
        return demandeService.findDemandeAndCafes(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage save(@ModelAttribute DemandeDTO demandeDTO) {
        return ResponseMessage.getSuccessMessage("YES!!");
    }
}
