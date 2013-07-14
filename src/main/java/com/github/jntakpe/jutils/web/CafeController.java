package com.github.jntakpe.jutils.web;

import com.github.jntakpe.fmk.service.GenericService;
import com.github.jntakpe.fmk.web.GenericController;
import com.github.jntakpe.jutils.domain.Cafe;
import com.github.jntakpe.jutils.service.CafeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
