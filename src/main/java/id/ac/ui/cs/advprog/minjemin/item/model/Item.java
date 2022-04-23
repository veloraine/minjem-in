package id.ac.ui.cs.advprog.minjemin.item.model;

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
@Table(name="item")

public class Item {

    @Id
    @Column(name="item_id")
    @GeneratedValue(generator="uuid2")
    @GenericGenerator(name="uuid2", strategy= "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(name="item_name")
    private String name;

    @Column(name="item_deskripsi")
    private String desc;

    @Column(name="item_harga")
    private int harga;

    @Column(name="item_status")
    private String status;

    @Column(name="id_pinjaman")
    private String idPinjaman;

    @Lob
    @Column(name="profile_pic")
    private byte[] profilePic;

}
