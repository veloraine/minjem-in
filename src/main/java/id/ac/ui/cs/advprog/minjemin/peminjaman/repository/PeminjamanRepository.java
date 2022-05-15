package id.ac.ui.cs.advprog.minjemin.peminjaman.repository;

import id.ac.ui.cs.advprog.minjemin.peminjaman.model.Peminjaman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeminjamanRepository extends JpaRepository<Peminjaman, String> {
    Peminjaman findPeminjamanById(String id);
    Peminjaman findPeminjamanByUserId(String id);
    Peminjaman findPeminjamanByItemId(String id);
    List<Peminjaman> findAllByUserId(String id);
}
