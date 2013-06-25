package com.github.jntakpe.jutils.web;

import com.github.jntakpe.jutils.domain.Item;
import com.github.jntakpe.jutils.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @RequestMapping(value="/batch", method = RequestMethod.GET)
    public String batch() {
        //Mettre un cron/quartz pour lancer le batch la nuit
        itemService.saveLdapItems();
        return "portal";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String display() {
        return "item_list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<Item> list() {
        return itemService.findAll();
    }
}
