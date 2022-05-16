package id.ac.ui.cs.advprog.minjemin.peminjaman.controller;

import id.ac.ui.cs.advprog.minjemin.auth.service.SecurityService;
import id.ac.ui.cs.advprog.minjemin.auth.service.UserService;
import id.ac.ui.cs.advprog.minjemin.item.service.ItemService;
import id.ac.ui.cs.advprog.minjemin.peminjaman.service.PeminjamanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/peminjaman")
public class PeminjamanController {

    @Autowired
    ItemService itemService;

    @Autowired
    SecurityService securityService;

    @Autowired
    UserService userService;

    @Autowired
    PeminjamanService peminjamanService;

    @GetMapping(path = "/pinjam/{id}")
    public String createPeminjaman(@PathVariable(value = "id") String id, Model model) {

        var user = securityService.findLoggedInUserDetails();
        if (user == null)  {
            return "redirect:/auth/login";
        }
        model.addAttribute("user", userService.findByUsername(user.getUsername()));
        model.addAttribute("item", itemService.getItemById(id));
        model.addAttribute("sessionId", securityService.findLoggedInUserDetails());
        return "peminjaman/pinjam";
    }

    @PostMapping(path = "/pinjam/{id}")
    public String createPeminjaman(@PathVariable(value = "id") String id, Model model,
                                   @RequestParam(value = "mulai") String tanggalMulai,
                                   @RequestParam(value = "selesai") String tanggalSelesai) {

        var result = peminjamanService.createPeminjaman(id, tanggalMulai, tanggalSelesai);
        model.addAttribute("result", result);
        model.addAttribute("sessionId", securityService.findLoggedInUserDetails());
        return "peminjaman/result";
    }
}
