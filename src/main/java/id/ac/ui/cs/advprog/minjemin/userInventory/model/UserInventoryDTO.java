package id.ac.ui.cs.advprog.minjemin.userInventory.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInventoryDTO {
    String nama;
    String base64;
    String desc;
    String tanggalMulai;
    String tanggalselesai;
    long durasi;
    long biaya;
    long harga;
    String itemId;
    String idPeminjam;
    String peminjamanId;
    String statusPembayaran;
    String statusPeminjaman;
}
