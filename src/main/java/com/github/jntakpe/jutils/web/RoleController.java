package com.github.jntakpe.jutils.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.github.jntakpe.fmk.service.GenericService;
import com.github.jntakpe.fmk.web.GenericController;
import com.github.jntakpe.jutils.domain.Role;
import com.github.jntakpe.jutils.domain.Utilisateur;
import com.github.jntakpe.jutils.service.RoleService;
import com.github.jntakpe.jutils.service.UtilisateurService;
import com.github.jntakpe.jutils.util.dto.UtilisateurRoleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

/**
 * Gestion de l'écrans des {@link Role}
 *
 * @author jntakpe
 */
@Controller
@RequestMapping("/admin/role")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class RoleController extends GenericController<Role> {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UtilisateurService utilisateurService;

    @Override
    protected GenericService<Role> getService() {
        return roleService;
    }

    @RequestMapping(value = "/assign", method = RequestMethod.GET)
    public ModelAndView displayAssign() {
        ModelAndView mv = new ModelAndView("assign_roles_list");
        return mv.addObject("roles", roleService.findAll());
    }

    @RequestMapping(value = "/assign/list", method = RequestMethod.GET)
    @ResponseBody
    public Set<UtilisateurRoleDTO> list() throws JsonProcessingException {
        return utilisateurService.findAllUtilisateursWithRoles();
    }

}
