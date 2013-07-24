package com.github.jntakpe.jutils.web;

import com.github.jntakpe.jutils.domain.Cafe;
import com.github.jntakpe.jutils.service.CafeService;
import com.github.jntakpe.jutils.service.DemandeService;
import com.github.jntakpe.jutils.util.dto.DemandeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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

}
