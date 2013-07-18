package com.github.jntakpe.jutils.web;

import com.github.jntakpe.jutils.domain.Demande;
import com.github.jntakpe.jutils.service.CafeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

/**
 * Contrôleur gérant les écrans relatifs à une demande de café
 *
 * @author jntakpe
 */
@Controller
@RequestMapping("/demande")
public class DemandeController {

    @Autowired
    private CafeService cafeService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView display() {
        ModelAndView mv = new ModelAndView("demande");
        mv.addObject(new Demande());
        return mv.addObject("cafes", cafeService.categorize());
    }

    @RequestMapping(value = "/image/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] displayImage(@PathVariable Long id) throws IOException {
        return cafeService.findOne(id).getImage();
    }
}
