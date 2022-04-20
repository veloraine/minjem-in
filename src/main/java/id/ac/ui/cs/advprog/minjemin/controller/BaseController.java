package id.ac.ui.cs.advprog.minjemin.controller;

import id.ac.ui.cs.advprog.minjemin.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaseController {

    @Autowired
    ItemService itemService;

    @GetMapping(value="/")
    public String homepage(Model model) {
        model.addAttribute("items", itemService.getItems());
        return "homepage";
    }
}
