package id.ac.ui.cs.advprog.minjemin.api;

import id.ac.ui.cs.advprog.minjemin.peminjaman.model.PeminjamanDetails;
import id.ac.ui.cs.advprog.minjemin.peminjaman.service.PeminjamanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api")

public class PeminjamanApi {

    @Autowired
    PeminjamanService peminjamanService;

    @GetMapping(path = "/{id}", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<Iterable<PeminjamanDetails>> getPeminjaman(@PathVariable(value = "id") String id){
        return ResponseEntity.ok(peminjamanService.getAllPeminjamanByUserId(id));
    }

    @GetMapping(path = "/pay/{id}", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<String> payPeminjaman(@PathVariable(value = "id") String id) {
        return ResponseEntity.ok(peminjamanService.payPeminjaman(id));
    }


}
