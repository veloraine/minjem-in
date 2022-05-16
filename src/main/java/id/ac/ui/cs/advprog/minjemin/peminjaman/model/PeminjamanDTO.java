package id.ac.ui.cs.advprog.minjemin.peminjaman.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PeminjamanDTO {
    String id;
    String user_id;
    String item_id;
    String tanggal_mulai;
    String tanggal_selesai;
    String status;
    String status_pembayaran;
}

