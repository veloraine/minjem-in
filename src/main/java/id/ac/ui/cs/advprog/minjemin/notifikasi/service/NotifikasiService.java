package id.ac.ui.cs.advprog.minjemin.notifikasi.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface NotifikasiService {
    List<Map<String, String>> peminjamanDeadline() throws ParseException;
}
