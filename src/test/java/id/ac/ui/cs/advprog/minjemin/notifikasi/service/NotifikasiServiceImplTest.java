package id.ac.ui.cs.advprog.minjemin.notifikasi.service;

import id.ac.ui.cs.advprog.minjemin.auth.model.User;
import id.ac.ui.cs.advprog.minjemin.auth.model.UserDTO;
import id.ac.ui.cs.advprog.minjemin.auth.repository.UserRepository;
import id.ac.ui.cs.advprog.minjemin.auth.security.MinjeminUserDetails;
import id.ac.ui.cs.advprog.minjemin.auth.service.SecurityService;
import id.ac.ui.cs.advprog.minjemin.item.model.Item;
import id.ac.ui.cs.advprog.minjemin.item.repository.ItemRepository;
import id.ac.ui.cs.advprog.minjemin.peminjaman.model.Peminjaman;
import id.ac.ui.cs.advprog.minjemin.peminjaman.repository.PeminjamanRepository;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotifikasiServiceImplTest {
    private UserDTO userDto;
    private User user;
    private Item item1;
    private Item item2;
    private MinjeminUserDetails minjeminUserDetails;
    private Peminjaman peminjamanDummy1;
    private Peminjaman peminjamanDummy2;

    @Mock
    PeminjamanRepository peminjamanRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    ItemRepository itemRepository;

    @Mock
    SecurityService securityService;

    @InjectMocks
    NotifikasiServiceImpl notifikasiService;


    @BeforeEach
    void setUp() {
        userDto = new UserDTO();
        userDto.setUsername("blax");
        userDto.setPassword("OKE123456789");
        userDto.setRoles("USER");
        userDto.setPasswordConfirm("OKE123456789");
        user = new User(userDto.getUsername(), userDto.getPassword(), userDto.getRoles());
        user.setId("user-1");
        user.setPasswordConfirm(userDto.getPasswordConfirm());
        minjeminUserDetails = new MinjeminUserDetails(user);

        item1 = Item.builder()
                .name("scouter")
                .desc("ini scouter")
                .harga(9000)
                .status("tersedia")
                .profilePic("scouter".getBytes())
                .build();
        item1.setId("item-1");

        item2 = Item.builder()
                .name("scouter")
                .desc("ini scouter")
                .harga(9000)
                .status("tersedia")
                .profilePic("scouter".getBytes())
                .build();
        item2.setId("item-2");

        // Hitung tanggal peminjaman dan pengembalian
        var sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        var tanggalPeminjaman = new Date();
        var tanggalPeminjamanInMillis = tanggalPeminjaman.getTime();
        var tanggalPengembalian = tanggalPeminjamanInMillis + 86400000;

        var tanggalPeminjamanFormatted = sdf.format(tanggalPeminjaman);
        var tanggalPengembalianFormatted = sdf.format(tanggalPengembalian);

        peminjamanDummy1 = new Peminjaman("pinjam-1", "user-1", "item-1",
                "2002-01-11", "2003-01-11", "menunggu persetujuan", "belum dibayar");
        peminjamanDummy1.setId("peminjaman-1");

        peminjamanDummy2 = new Peminjaman("pinjam-2", "user-1", "item-2",
                tanggalPeminjamanFormatted, tanggalPengembalianFormatted, "menunggu persetujuan", "belum dibayar");
        peminjamanDummy2.setId("peminjaman-2");
    }

    @Test
    void testShouldReturnCorrectDataTypeWhenServiceCalled() throws Exception {
        List<Peminjaman> peminjamanDeadline = new ArrayList<>();
        peminjamanDeadline.add(peminjamanDummy2);

        when(securityService.findLoggedInUserDetails()).thenReturn(minjeminUserDetails);
        when(userRepository.findByUsername("blax")).thenReturn(Optional.ofNullable(user));
        when(peminjamanRepository.findAllByUserId("user-1")).thenReturn(peminjamanDeadline);
        when(itemRepository.findItemById("item-2")).thenReturn(item2);

        var res = notifikasiService.peminjamanDeadline();
        assertInstanceOf(List.class, res);
        assertInstanceOf(Map.class, res.get(0));
    }
}
