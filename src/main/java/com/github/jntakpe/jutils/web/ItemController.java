package com.github.jntakpe.jutils.web;

import com.github.jntakpe.jutils.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Contrôleur gérant les {@link com.github.jntakpe.jutils.domain.Item}
 *
 * @author jntakpe
 */
@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping(method = RequestMethod.GET)
    public String batch() {
        itemService.saveLdapItems();
        return "portal";
    }
}
