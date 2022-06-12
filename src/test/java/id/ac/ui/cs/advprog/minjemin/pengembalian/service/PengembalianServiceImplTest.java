package id.ac.ui.cs.advprog.minjemin.pengembalian.service;

import id.ac.ui.cs.advprog.minjemin.auth.model.User;
import id.ac.ui.cs.advprog.minjemin.auth.model.UserDTO;
import id.ac.ui.cs.advprog.minjemin.auth.security.MinjeminUserDetails;
import id.ac.ui.cs.advprog.minjemin.auth.service.SecurityService;
import id.ac.ui.cs.advprog.minjemin.auth.service.UserService;
import id.ac.ui.cs.advprog.minjemin.item.model.Item;
import id.ac.ui.cs.advprog.minjemin.item.repository.ItemRepository;
import id.ac.ui.cs.advprog.minjemin.item.service.ItemService;
import id.ac.ui.cs.advprog.minjemin.peminjaman.model.Peminjaman;
import id.ac.ui.cs.advprog.minjemin.peminjaman.repository.PeminjamanRepository;
import id.ac.ui.cs.advprog.minjemin.peminjaman.service.PeminjamanServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PengembalianServiceImplTest {
    private UserDTO userDto;
    private User user;
    private Item item;
    private MinjeminUserDetails minjeminUserDetails;
    private Peminjaman peminjamanDummy;

    @Mock
    PeminjamanRepository peminjamanRepository;

    @Mock
    ItemRepository itemRepository;

    @Mock
    ItemService itemService;

    @Mock
    UserService userService;

    @Mock
    SecurityService securityService;

    @InjectMocks
    PengembalianServiceImpl pengembalianService;

    @InjectMocks
    PeminjamanServiceImpl peminjamanService;

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

        item = Item.builder()
                .name("scouter")
                .desc("ini scouter")
                .harga(9000)
                .status("tersedia")
                .profilePic("scouter".getBytes())
                .build();
        item.setId("item-1");

        peminjamanDummy = new Peminjaman("pinjam-1", "user-1", "item-1",
                "2002-01-11", "2003-01-11", "menunggu persetujuan", "belum dibayar");
        peminjamanDummy.setId("peminjaman-1");
    }

    @Test
    void testBarangBerhasilDikembalikan() {
        when(securityService.findLoggedInUserDetails()).thenReturn(minjeminUserDetails);
        when(userService.findByUsername("blax")).thenReturn(user);
        when(itemRepository.getById("item-1")).thenReturn(item);
        when(peminjamanRepository.findPeminjamanById("peminjaman-1")).thenReturn(peminjamanDummy);

        var res = peminjamanService.createPeminjaman("item-1", "2022-09-09", "2022-09-11");
        assertEquals("Barang berhasil dipinjam", res);

        res = peminjamanService.createPeminjaman("item-1", "2022-11-09", "2022-11-11");
        assertEquals("Status barang sudah tidak tersedia", res);

        var kembali = pengembalianService.pengembalian("peminjaman-1");
        assertEquals("Barang berhasil dikembalikan!", kembali);
    }
}
