package id.ac.ui.cs.advprog.minjemin.notifikasi.service;

import id.ac.ui.cs.advprog.minjemin.auth.repository.UserRepository;
import id.ac.ui.cs.advprog.minjemin.auth.service.SecurityService;
import id.ac.ui.cs.advprog.minjemin.item.repository.ItemRepository;
import id.ac.ui.cs.advprog.minjemin.peminjaman.model.Peminjaman;
import id.ac.ui.cs.advprog.minjemin.peminjaman.repository.PeminjamanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class NotifikasiServiceImpl implements NotifikasiService {
    @Autowired
    PeminjamanRepository peminjamanRepository;
    
    @Autowired
    SecurityService securityService;
    
    @Autowired
    UserRepository userRepository;

    @Autowired
    ItemRepository itemRepository;
    
    @Override
    public List<Map> peminjamanDeadline() throws ParseException {
        var usernamePengguna = securityService.findLoggedInUserDetails().getUsername();
        var userLogin = userRepository.findByUsername(usernamePengguna).get();
        var idPengguna = userLogin.getId();

        List<Peminjaman> userInventory = peminjamanRepository.findAllByUserId(idPengguna);
        List<Map> peminjamanDekatDeadline = new ArrayList<>();
        
        for (Peminjaman peminjaman : userInventory) {
            var tanggalSelesai = peminjaman.getTanggalSelesai();
            var calcTanggalSelesai = tanggalSelesai.substring(8, 10) + '/' +  tanggalSelesai.substring(5, 7) + '/' + tanggalSelesai.substring(0, 4);
            var sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            var parsedtanggalSelesai = sdf.parse(calcTanggalSelesai);
            var tanggalSekarang = new Date();

            var diffInMillies = Math.abs(tanggalSekarang.getTime() - parsedtanggalSelesai.getTime());
            var durasi = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

            if (durasi <= 2) {
                var itemDipinjam = itemRepository.findItemById(peminjaman.getItemId());
                var namaBarang = itemDipinjam.getName();
                Map<String, String> map = new HashMap<>();
                map.put("namaBarang", namaBarang);
                map.put("durasi", String.valueOf(durasi));
                peminjamanDekatDeadline.add(map);
            }
        }
        
        return peminjamanDekatDeadline;
    }
}
