package id.ac.ui.cs.advprog.minjemin.item.controller;

import id.ac.ui.cs.advprog.minjemin.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(path = "/admin")
public class ItemController {

    private static final String REDIRECT = "redirect:/admin/";

    @Autowired
    ItemService itemService;

    @GetMapping(path = "/")
    public String dashboardAdmin(Model model) {
        model.addAttribute("items", itemService.getItems());
        return "admin/dashboard";
    }

    @GetMapping(value = "/create")
    public String createItem(Model model) {
        return "items/add_item";
    }

    @PostMapping(value = "/create")
    public String createItem(Model model,
                             @RequestParam(value = "name") String name,
                             @RequestParam(value = "desc") String desc,
                             @RequestParam(value = "harga") int harga,
                             @RequestParam(value = "file") MultipartFile file) {
        try {
            itemService.createItem(name, desc, harga, file);
        }
        catch (Exception e) {
            return REDIRECT;
        }
        return REDIRECT;
    }

    @GetMapping(path = "/update/{id}")
    public String updateItem(@PathVariable(value = "id") String id, Model model) {
        model.addAttribute("item", itemService.getItemById(id));
        return "items/update";
    }

    @PostMapping(value = "/update/{id}")
    public String updateItem(@PathVariable(value = "id") String id, Model model,
                             @RequestParam(value = "name") String name,
                             @RequestParam(value = "desc") String desc,
                             @RequestParam(value = "harga") int harga,
                             @RequestParam(value = "file") MultipartFile file) {
        try {
            itemService.updateItem(id, name, desc, harga, file);

        }
        catch (Exception e) {
            return REDIRECT;
        }
        return REDIRECT;
    }

    @GetMapping(path = "/delete/{id}")
    public String deleteItem(@PathVariable(value = "id") String id, Model model) {
        itemService.deleteItem(id);
        return REDIRECT;
    }
}
