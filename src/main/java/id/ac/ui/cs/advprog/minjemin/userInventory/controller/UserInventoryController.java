package id.ac.ui.cs.advprog.minjemin.userInventory.controller;

import id.ac.ui.cs.advprog.minjemin.auth.security.MinjeminUserDetails;
import id.ac.ui.cs.advprog.minjemin.auth.service.SecurityService;
import id.ac.ui.cs.advprog.minjemin.userInventory.service.UserInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.ParseException;

@Controller
public class UserInventoryController {
    @Autowired
    UserInventoryService userInventoryService;

    @Autowired
    SecurityService securityService;


    @GetMapping(path="/user-inventory")
    public String showUserInventory(Model model) throws ParseException {
        model.addAttribute("userInventories", userInventoryService.showUserInventory());
        model.addAttribute("sessionId", securityService.findLoggedInUserDetails());
        return "user-inventory/show";
    }
}
