package id.ac.ui.cs.advprog.minjemin.peminjaman.util;

import id.ac.ui.cs.advprog.minjemin.auth.service.SecurityService;
import id.ac.ui.cs.advprog.minjemin.auth.service.UserService;
import id.ac.ui.cs.advprog.minjemin.item.repository.ItemRepository;
import id.ac.ui.cs.advprog.minjemin.peminjaman.model.Peminjaman;
import id.ac.ui.cs.advprog.minjemin.peminjaman.repository.PeminjamanRepository;

public class PeminjamanCreator {

    private static final String STATUSMENUNGGU = "menunggu persetujuan";

    private static PeminjamanCreator peminjamanCreator;

    private PeminjamanCreator(){}

    public static PeminjamanCreator getInstance() {
        if (peminjamanCreator == null) {
            peminjamanCreator = new PeminjamanCreator();
        }
        return peminjamanCreator;
    }

    public synchronized String createPeminjaman(String itemId, String tanggalMulai, String tanggalSelesai,
                                       PeminjamanRepository peminjamanRepository,
                                       ItemRepository itemRepository,
                                       UserService userService,
                                       SecurityService securityService) {
        var userId = getUserId(securityService, userService);

        var isValid = cekTanggal(tanggalMulai, tanggalSelesai);
        if (!isValid) {
            return "Tanggal tidak valid";
        }

        var msg = updateItemStatus(itemId, itemRepository);
        if (msg.equals("Status barang sudah tidak tersedia")) {
            return msg;
        }

        var peminjamanBuilder = Peminjaman.builder();
        var peminjamanValues = peminjamanBuilder
                .userId(userId)
                .itemId(itemId)
                .tanggalMulai(tanggalMulai)
                .tanggalSelesai(tanggalSelesai)
                .status(STATUSMENUNGGU)
                .statusPembayaran("belum dibayar");
        var peminjaman = peminjamanValues.build();

        peminjamanRepository.save(peminjaman);
        return "Barang berhasil dipinjam";
    }

    public String getUserId(SecurityService securityService, UserService userService) {
        var userDetails = securityService.findLoggedInUserDetails();
        var username = userDetails.getUsername();
        var user = userService.findByUsername(username);
        return user.getId();
    }

    public String updateItemStatus(String itemId, ItemRepository itemRepository) {
        var item = itemRepository.getById(itemId);
        var itemStatus = item.getStatus();
        if (itemStatus.equals("tidak tersedia") || itemStatus.equals(STATUSMENUNGGU)) {
            return "Status barang sudah tidak tersedia";
        }
        item.setStatus(STATUSMENUNGGU);
        return "Status barang berhasil diupdate";
    }
    public boolean cekTanggal(String tanggalMulai, String tanggalSelesai) {
        var mulai = tanggalMulai.split("-");
        var selesai = tanggalSelesai.split("-");

        var numMulai = String.join("", mulai);
        var numSelesai = String.join("", selesai);

        return Integer.parseInt(numSelesai) > Integer.parseInt(numMulai);
    }
}
