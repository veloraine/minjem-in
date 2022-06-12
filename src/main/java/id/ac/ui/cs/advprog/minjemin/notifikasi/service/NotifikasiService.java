package id.ac.ui.cs.advprog.minjemin.notifikasi.service;

import id.ac.ui.cs.advprog.minjemin.peminjaman.model.Peminjaman;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface NotifikasiService {
    List<Map> peminjamanDeadline() throws ParseException;
}
