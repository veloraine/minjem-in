package id.ac.ui.cs.advprog.minjemin.userInventory.controller;

import id.ac.ui.cs.advprog.minjemin.auth.service.SecurityService;
import id.ac.ui.cs.advprog.minjemin.auth.service.UserService;
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

    @Autowired
    UserService userService;


    @GetMapping(path="/user-inventory")
    public String showUserInventory(Model model){
        try {
            var userDetails = securityService.findLoggedInUserDetails();
            var username = userDetails.getUsername();
            var user = userService.findByUsername(username);
            var userInventory = userInventoryService.showUserInventory();
            model.addAttribute("userInventories", userInventory);
            model.addAttribute("sessionId", userDetails);
            model.addAttribute("userId", user.getId());
        } catch (ParseException e) {
        }
        return "user-inventory/show";
    }
}
