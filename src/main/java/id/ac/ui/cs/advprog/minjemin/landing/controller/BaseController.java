package id.ac.ui.cs.advprog.minjemin.landing.controller;

import id.ac.ui.cs.advprog.minjemin.auth.service.SecurityService;
import id.ac.ui.cs.advprog.minjemin.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaseController {

    @Autowired
    ItemService itemService;

    @Autowired
    SecurityService securityService;

    @GetMapping(value="/")
    public String homepage(Model model) {
        model.addAttribute("items", itemService.getItems());
        model.addAttribute("sessionId", securityService.findLoggedInUserDetails());
        return "homepage";
    }
}
