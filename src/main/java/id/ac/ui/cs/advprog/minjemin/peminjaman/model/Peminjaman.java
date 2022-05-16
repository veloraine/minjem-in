package id.ac.ui.cs.advprog.minjemin.peminjaman.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="peminjaman")
public class Peminjaman {

    @Id
    @Column(name="peminjaman_id")
    @GeneratedValue(generator="uuid2")
    @GenericGenerator(name="uuid2", strategy= "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(name="user_id")
    private String userId;

    @Column(name="item_id")
    private String itemId;

    @Column(name="tanggal_mulai")
    private String tanggalMulai;

    @Column(name="tanggal_selesai")
    private String tanggalSelesai;

    @Column(name="status")
    private String status;

    @Column(name="status_pembayaran")
    private String statusPembayaran;


}
