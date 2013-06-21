package com.github.jntakpe.jutils.web;

import com.github.jntakpe.jutils.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author jntakpe
 */
@Controller
@RequestMapping("/toto")
public class Toto {

    @Autowired
    private ItemService itemService;

    @RequestMapping(method = RequestMethod.GET)
    public String display() {
        itemService.majLdapItems();
        return "portal";
    }
}
