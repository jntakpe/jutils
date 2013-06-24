package com.github.jntakpe.jutils.web;

import com.github.jntakpe.fmk.util.dto.ResponseMessage;
import com.github.jntakpe.jutils.domain.Age;
import com.github.jntakpe.jutils.service.AgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * Contrôlleur gérant l'écran de calcul sur l'entité {@link Age}
 *
 * @author jntakpe
 */
@Controller
@RequestMapping("/age")
public class AgeController {

    @Autowired
    private AgeService ageService;

    @RequestMapping(method = RequestMethod.GET)
    public String display() {
        return "age";
    }

    @RequestMapping(value = "/calc", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage calc(@RequestParam Date birthdate) {
        return ResponseMessage.getSuccessMessage(ageService.calcAge(birthdate));
    }
}
