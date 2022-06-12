package id.ac.ui.cs.advprog.minjemin.pengembalian.controller;

import id.ac.ui.cs.advprog.minjemin.auth.service.SecurityService;
import id.ac.ui.cs.advprog.minjemin.pengembalian.service.PengembalianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/pengembalian")
public class PengembalianController {
    @Autowired
    SecurityService securityService;

    @Autowired
    PengembalianService pengembalianService;

    @PostMapping(path = "/kembali/{id}")
    public String initiatePengembalian(@PathVariable(value = "id") String id, Model model) {
        var result = pengembalianService.pengembalian(id);
        model.addAttribute("result", result);
        model.addAttribute("sessionId", securityService.findLoggedInUserDetails());
        return "redirect:/user-inventory";
    }
}
