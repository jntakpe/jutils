package com.github.jntakpe.jutils.web;

import com.github.jntakpe.fmk.service.MessageManager;
import com.github.jntakpe.fmk.util.dto.ResponseMessage;
import com.github.jntakpe.jutils.domain.Item;
import com.github.jntakpe.jutils.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Contrôleur gérant les {@link com.github.jntakpe.jutils.domain.Item}
 *
 * @author jntakpe
 */
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private MessageManager messageManager;

    @RequestMapping(value = "/admin/batch/item", method = RequestMethod.GET)
    public ModelAndView batch() {
        ModelAndView mv = new ModelAndView("portal");
        itemService.saveLdapItems();
        ResponseMessage responseMessage = ResponseMessage.getSuccessMessage(messageManager.getMessage("update.item"));
        return mv.addObject("responseMessage", responseMessage);
    }

    @RequestMapping(value = "/item", method = RequestMethod.GET)
    public String display() {
        return "item_list";
    }

    @RequestMapping(value = "/item/list", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<Item> list() {
        return itemService.findAll();
    }
}
