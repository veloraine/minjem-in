package id.ac.ui.cs.advprog.minjemin.item.controller;

import id.ac.ui.cs.advprog.minjemin.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class DetailController {
    @Autowired
    ItemService itemService;

    @GetMapping(value="/items/detail/{id}")
    public String itemDetail(@PathVariable(value="id") String id, Model model) {
        model.addAttribute("item", itemService.getItemObject(id));
        return "items/detail";
    }
}
