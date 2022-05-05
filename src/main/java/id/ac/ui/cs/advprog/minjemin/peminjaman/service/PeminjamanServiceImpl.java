package id.ac.ui.cs.advprog.minjemin.peminjaman.service;

import id.ac.ui.cs.advprog.minjemin.auth.service.SecurityService;
import id.ac.ui.cs.advprog.minjemin.auth.service.UserService;
import id.ac.ui.cs.advprog.minjemin.item.repository.ItemRepository;
import id.ac.ui.cs.advprog.minjemin.peminjaman.repository.PeminjamanRepository;
import id.ac.ui.cs.advprog.minjemin.peminjaman.util.PeminjamanCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class PeminjamanServiceImpl implements PeminjamanService{

    @Autowired
    PeminjamanRepository peminjamanRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    UserService userService;

    @Autowired
    SecurityService securityService;

    @Override
    public String createPeminjaman(String itemId, String tanggalMulai, String tanggalSelesai) {
        var peminjamanCreator = PeminjamanCreator.getInstance();
        return peminjamanCreator.createPeminjaman(itemId, tanggalMulai, tanggalSelesai, peminjamanRepository, itemRepository, userService, securityService);
    }

}
