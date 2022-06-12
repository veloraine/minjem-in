package id.ac.ui.cs.advprog.minjemin.peminjaman.service;

import id.ac.ui.cs.advprog.minjemin.peminjaman.model.Peminjaman;
import id.ac.ui.cs.advprog.minjemin.peminjaman.model.PeminjamanDTO;
import id.ac.ui.cs.advprog.minjemin.peminjaman.model.PeminjamanDetails;

import java.util.List;


public interface PeminjamanService {
    String createPeminjaman(String itemId, String tanggalMulai, String tanggalSelesai);
    void tolakPeminjaman(String id);
    void terimaPeminjaman(String id);
    void batalkanPeminjaman(String id);
    Peminjaman getPeminjamanByItemId(String itemId);
    List<PeminjamanDTO> getAllPeminjaman();
    List<PeminjamanDetails> getAllPeminjamanByUserId(String userId);
    String payPeminjaman(String id);
}
