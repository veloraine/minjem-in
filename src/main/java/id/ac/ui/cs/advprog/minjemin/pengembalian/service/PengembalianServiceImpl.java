package id.ac.ui.cs.advprog.minjemin.pengembalian.service;

import id.ac.ui.cs.advprog.minjemin.auth.service.SecurityService;
import id.ac.ui.cs.advprog.minjemin.item.service.ItemService;
import id.ac.ui.cs.advprog.minjemin.peminjaman.repository.PeminjamanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class PengembalianServiceImpl implements PengembalianService {
    @Autowired
    SecurityService securityService;

    @Autowired
    PeminjamanRepository peminjamanRepository;

    @Autowired
    ItemService itemService;

    @Override
    public String pengembalian(String peminjamanId) {
        // Cari item yang dikembalikan
        var peminjamanDikembalikan = peminjamanRepository.findPeminjamanById(peminjamanId);
        var idItemDikembalikan = peminjamanDikembalikan.getItemId();

        // Ubah status menjadi tersedia & hapus peminjaman
        itemService.updateStatusItem(idItemDikembalikan, 2);
        peminjamanRepository.deletePeminjamanById(peminjamanId);

        return "Barang berhasil dikembalikan!";
    }
}