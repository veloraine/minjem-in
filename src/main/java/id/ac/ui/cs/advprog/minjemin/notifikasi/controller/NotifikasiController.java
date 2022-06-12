package id.ac.ui.cs.advprog.minjemin.notifikasi.controller;

import id.ac.ui.cs.advprog.minjemin.auth.service.SecurityService;
import id.ac.ui.cs.advprog.minjemin.notifikasi.service.NotifikasiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@ResponseBody
@RequestMapping("/notifikasi")
public class NotifikasiController {
    @Autowired
    NotifikasiService notifikasiService;

    @GetMapping(value = "")
    public List<Map> showNotification() {
        List<Map> peminjamanDekatDeadline = new ArrayList<>();
        try {
            peminjamanDekatDeadline = notifikasiService.peminjamanDeadline();
        } catch (ParseException e) {
            return new ArrayList<>();
        }
        return peminjamanDekatDeadline;
    }
}
