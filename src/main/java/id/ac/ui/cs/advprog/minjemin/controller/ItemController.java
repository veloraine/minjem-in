package id.ac.ui.cs.advprog.minjemin.controller;

import id.ac.ui.cs.advprog.minjemin.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class ItemController {

    @Autowired
    ItemService itemService;

    @GetMapping(value = "/add-item")
    public String createItem(Model model) {
        return "add_item";
    }

    @PostMapping(value = "/add-item")
    public String createItem(Model model,
                             @RequestParam(value = "name") String name,
                             @RequestParam(value = "desc") String desc,
                             @RequestParam(value = "harga") int harga,
                             @RequestParam(value = "file") MultipartFile file) {
        try {
            itemService.createItem(name, desc, harga, file);
        }
        catch (Exception e) {
            return "redirect:/";
        }
        return "redirect:/";
    }

}
