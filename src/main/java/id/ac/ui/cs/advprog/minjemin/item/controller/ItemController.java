package id.ac.ui.cs.advprog.minjemin.item.controller;

import id.ac.ui.cs.advprog.minjemin.auth.service.UserService;
import id.ac.ui.cs.advprog.minjemin.item.service.ItemService;
import id.ac.ui.cs.advprog.minjemin.peminjaman.service.PeminjamanService;
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

    @Autowired
    PeminjamanService peminjamanService;

    @Autowired
    UserService userService;

    @GetMapping(path = "/")
    public String dashboardAdmin(Model model) {
        model.addAttribute("items", itemService.getItems());
        return "admin/dashboard";
    }

    @GetMapping(path = "/tabel-pengajuan")
    public String tabelPengajuan(Model model) {
        model.addAttribute("peminjaman", peminjamanService.getAllPeminjaman());
        model.addAttribute("itemService", itemService);
        model.addAttribute("userService", userService);
        return "admin/tabel_pengajuan";
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

    @GetMapping(path = "/tabel-pengajuan/terimaPeminjaman/{id}")
    public String terimaPinjam(@PathVariable(value = "id") String id, Model model) {
        peminjamanService.terimaPeminjaman(id);
        return "redirect:/admin/tabel-pengajuan/";
    }

    @GetMapping(path = "/tabel-pengajuan/tolakPeminjaman/{id}")
    public String tolakPinjam(@PathVariable(value = "id") String id, Model model) {
        peminjamanService.tolakPeminjaman(id);
        return "redirect:/admin/tabel-pengajuan/";
    }

}
