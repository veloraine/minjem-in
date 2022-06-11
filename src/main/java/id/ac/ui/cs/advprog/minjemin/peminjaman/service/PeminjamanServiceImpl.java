package id.ac.ui.cs.advprog.minjemin.peminjaman.service;

import id.ac.ui.cs.advprog.minjemin.auth.service.SecurityService;
import id.ac.ui.cs.advprog.minjemin.auth.service.UserService;
import id.ac.ui.cs.advprog.minjemin.item.repository.ItemRepository;
import id.ac.ui.cs.advprog.minjemin.item.service.ItemService;
import id.ac.ui.cs.advprog.minjemin.peminjaman.model.Peminjaman;
import id.ac.ui.cs.advprog.minjemin.peminjaman.model.PeminjamanDTO;
import id.ac.ui.cs.advprog.minjemin.peminjaman.model.PeminjamanDetails;
import id.ac.ui.cs.advprog.minjemin.peminjaman.repository.PeminjamanRepository;
import id.ac.ui.cs.advprog.minjemin.peminjaman.util.PeminjamanCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class PeminjamanServiceImpl implements PeminjamanService{

    @Autowired
    PeminjamanRepository peminjamanRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemService itemService;

    @Autowired
    UserService userService;

    @Autowired
    SecurityService securityService;

    @Override
    public String createPeminjaman(String itemId, String tanggalMulai, String tanggalSelesai) {
        var peminjamanCreator = PeminjamanCreator.getInstance();
        return peminjamanCreator.createPeminjaman(itemId, tanggalMulai, tanggalSelesai, peminjamanRepository, itemRepository, userService, securityService);
    }

    @Override
    public void tolakPeminjaman(String id) {
        Peminjaman pinjam = peminjamanRepository.findPeminjamanById(id);
        pinjam.setStatus("Ditolak");
        itemService.updateStatusItem(pinjam.getItemId(), 2);
    }

    @Override
    public void terimaPeminjaman(String id) {
        Peminjaman pinjam = peminjamanRepository.findPeminjamanById(id);
        pinjam.setStatus("Diterima");
        itemService.updateStatusItem(pinjam.getItemId(), 1);
    }

    @Override
    public Peminjaman getPeminjamanByItemId(String itemId){
        return peminjamanRepository.findPeminjamanByUserId(itemId);
    }

    @Override
    public List<PeminjamanDTO> getAllPeminjaman() {
        List<Peminjaman> peminjaman = peminjamanRepository.findAll();
        List<PeminjamanDTO> peminjamanDTO = new ArrayList<>();
        for (Peminjaman pinjam: peminjaman) {
            var objectDTO = new PeminjamanDTO(pinjam.getId(), pinjam.getUserId(), pinjam.getItemId(),
                    pinjam.getTanggalMulai(), pinjam.getTanggalSelesai(), pinjam.getStatus(), pinjam.getStatusPembayaran());
            peminjamanDTO.add(objectDTO);
        }
        return peminjamanDTO;
    }

    @Override
    public List<PeminjamanDetails> getAllPeminjamanByUserId(String userId) {
        List<Peminjaman> tempPeminjaman = peminjamanRepository.findAllByUserId(userId);
        List<PeminjamanDetails> peminjamanDetailsList = new ArrayList<>();
        for (Peminjaman p: tempPeminjaman) {
            var item = itemRepository.findItemById(p.getItemId());

            var peminjamanDetails = new PeminjamanDetails(
                    p.getId(),
                    p.getUserId(),
                    item.getName(),
                    p.getTanggalMulai(),
                    p.getTanggalSelesai(),
                    p.getStatus(),
                    p.getStatusPembayaran(),
                    item.getHarga()
            );
            peminjamanDetailsList.add(peminjamanDetails);
        }
        return peminjamanDetailsList;
    }

    @Override
    public String payPeminjaman(String id) {
        var peminjaman = peminjamanRepository.findPeminjamanById(id);
        peminjaman.setStatusPembayaran("dibayar");
        return "Status barang berhasil diubah";
    }
}
