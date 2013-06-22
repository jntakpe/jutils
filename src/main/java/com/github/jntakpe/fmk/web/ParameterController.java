package com.github.jntakpe.fmk.web;

import com.github.jntakpe.fmk.domain.Parameter;
import com.github.jntakpe.fmk.service.GenericService;
import com.github.jntakpe.fmk.service.ParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Contrôleur de l'écran de paramétrage de l'application
 *
 * @author jntakpe
 */
@Controller
@RequestMapping("/admin/param")
public class ParameterController extends GenericController<Parameter> {

    @Autowired
    private ParameterService parameterService;

    @Override
    protected GenericService<Parameter> getService() {
        return parameterService;
    }
}
