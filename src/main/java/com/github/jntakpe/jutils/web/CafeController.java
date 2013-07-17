package com.github.jntakpe.jutils.web;

import com.github.jntakpe.fmk.service.GenericService;
import com.github.jntakpe.fmk.web.GenericController;
import com.github.jntakpe.jutils.domain.Cafe;
import com.github.jntakpe.jutils.service.CafeService;
import com.github.jntakpe.jutils.util.constants.Categorie;
import com.github.jntakpe.jutils.util.constants.ProfilAromatique;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;

/**
 * Contrôleur des écrans relatifs à la gestion du café
 *
 * @author jntakpe
 */
@Controller
@RequestMapping("/cafe")
public class CafeController extends GenericController<Cafe> {

    @Autowired
    private CafeService cafeService;

    @Override
    protected GenericService<Cafe> getService() {
        return cafeService;
    }

    @Override
    public ModelAndView detail() throws Exception {
        return enrichMV(super.detail());
    }

    @Override
    public ModelAndView detail(@PathVariable Long id) {
        return enrichMV(super.detail(id));
    }

    private ModelAndView enrichMV(ModelAndView mv) {
        mv.addObject("numbers", new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        mv.addObject("profils", ProfilAromatique.values());
        return mv.addObject("categories", Categorie.values());
    }

    @InitBinder
    public void imageBinder(WebDataBinder binder) {
        binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
    }

}
