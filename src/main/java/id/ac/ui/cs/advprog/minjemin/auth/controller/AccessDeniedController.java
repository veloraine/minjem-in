package id.ac.ui.cs.advprog.minjemin.auth.controller;

import id.ac.ui.cs.advprog.minjemin.auth.model.User;
import id.ac.ui.cs.advprog.minjemin.auth.security.MinjeminUserDetails;
import id.ac.ui.cs.advprog.minjemin.auth.service.SecurityService;
import id.ac.ui.cs.advprog.minjemin.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AccessDeniedController {
    @Autowired
    private SecurityService securityService;

    @GetMapping("/access-denied")
    public String accessDenied(Model model) {
        model.addAttribute("sessionId", securityService.findLoggedInUserDetails());
        return "access_denied";
    }
}

