package id.ac.ui.cs.advprog.minjemin.userinventory.service;

import id.ac.ui.cs.advprog.minjemin.auth.model.User;
import id.ac.ui.cs.advprog.minjemin.auth.repository.UserRepository;
import id.ac.ui.cs.advprog.minjemin.auth.service.SecurityService;
import id.ac.ui.cs.advprog.minjemin.item.repository.ItemRepository;
import id.ac.ui.cs.advprog.minjemin.item.util.ImageProcessor;
import id.ac.ui.cs.advprog.minjemin.peminjaman.model.Peminjaman;
import id.ac.ui.cs.advprog.minjemin.peminjaman.repository.PeminjamanRepository;
import id.ac.ui.cs.advprog.minjemin.userinventory.model.UserInventoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class UserInventoryServiceImpl implements UserInventoryService {
    private User userLogin;

    @Autowired
    SecurityService securityService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PeminjamanRepository peminjamanRepository;

    @Autowired
    ItemRepository itemRepository;

    @Override
    @Transactional
    public List<UserInventoryDTO> showUserInventory() throws ParseException {
        var usernamePengguna = securityService.findLoggedInUserDetails().getUsername();
        var userLoginOptional = userRepository.findByUsername(usernamePengguna);
        if (userLoginOptional.isPresent()) {
            userLogin = userLoginOptional.get();
        }
        var idPengguna = userLogin.getId();

        List<UserInventoryDTO> userInventories = new ArrayList<>();
        var imageProcessor = ImageProcessor.getInstance();

        List<Peminjaman> userInventory = peminjamanRepository.findAllByUserId(idPengguna);
        for (Peminjaman item : userInventory) {
            var itemDipinjam = itemRepository.findItemById(item.getItemId());
            byte[] profileByte = itemDipinjam.getProfilePic();
            var encode64 = imageProcessor.generateStringImage(profileByte);


            var nama = itemDipinjam.getName();
            var desc = itemDipinjam.getDesc();
            var tanggalMulai = item.getTanggalMulai();
            var tanggalSelesai = item.getTanggalSelesai();
            var itemId = item.getItemId();
            var idPeminjam = item.getUserId();
            var peminjamanId = item.getId();
            var harga = itemDipinjam.getHarga();
            var statusPembayaran = item.getStatusPembayaran();
            var statusPeminjaman = item.getStatus();

            var calcTanggalMulai = tanggalMulai.substring(8, 10) + '/' +  tanggalMulai.substring(5, 7) + '/' + tanggalMulai.substring(0, 4);
            var sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            var parsedTanggalMulai = sdf.parse(calcTanggalMulai);
            var calcTanggalSelesai = tanggalSelesai.substring(8, 10) + '/' +  tanggalSelesai.substring(5, 7) + '/' + tanggalSelesai.substring(0, 4);
            var parsedTanggalSelesai = sdf.parse(calcTanggalSelesai);

            long diffInMillies = Math.abs(parsedTanggalSelesai.getTime() - parsedTanggalMulai.getTime());
            long durasi = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            long biaya = durasi * itemDipinjam.getHarga();

            var userInventoryDTO = new UserInventoryDTO(nama, encode64, desc,tanggalMulai, tanggalSelesai, durasi, biaya, harga, itemId, idPeminjam, peminjamanId, statusPembayaran, statusPeminjaman);
            userInventories.add(userInventoryDTO);
        }
        return userInventories;
    }
}
