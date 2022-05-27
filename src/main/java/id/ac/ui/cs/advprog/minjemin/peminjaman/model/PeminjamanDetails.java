package id.ac.ui.cs.advprog.minjemin.peminjaman.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PeminjamanDetails {
    String id;
    String userId;
    String itemName;
    String tanggalMulai;
    String tanggalSelesai;
    String status;
    String statusPembayaran;
    int hargaBarang;
}
