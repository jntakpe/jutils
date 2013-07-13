package com.github.jntakpe.jutils.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.jntakpe.fmk.service.GenericService;
import com.github.jntakpe.fmk.service.MessageManager;
import com.github.jntakpe.fmk.util.FmkUtils;
import com.github.jntakpe.fmk.util.constant.LogLevel;
import com.github.jntakpe.fmk.util.dto.ResponseMessage;
import com.github.jntakpe.fmk.web.GenericController;
import com.github.jntakpe.jutils.domain.Role;
import com.github.jntakpe.jutils.service.RoleService;
import com.github.jntakpe.jutils.service.UtilisateurService;
import com.github.jntakpe.jutils.util.dto.UtilisateurRoleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
public class RoleController extends GenericController<Role> {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private MessageManager messageManager;

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

    @RequestMapping(value = "/assign", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage assign(@RequestParam Long id, @RequestParam String role, @RequestParam boolean hasRole) {
        String username = roleService.assignRole(id, role, hasRole);
        String action = hasRole ? "retiré" : "assigné";
        messageManager.logMessage("MSG30000", LogLevel.INFO, FmkUtils.getCurrentUsername(), action, role, username);
        String alertMsg = messageManager.getMessage("role.assign", role, action, username);
        return ResponseMessage.getSuccessMessage(alertMsg);
    }

}
