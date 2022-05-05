package id.ac.ui.cs.advprog.minjemin.peminjaman.service;

import id.ac.ui.cs.advprog.minjemin.auth.model.User;
import id.ac.ui.cs.advprog.minjemin.auth.model.UserDTO;
import id.ac.ui.cs.advprog.minjemin.auth.security.MinjeminUserDetails;
import id.ac.ui.cs.advprog.minjemin.auth.service.SecurityService;
import id.ac.ui.cs.advprog.minjemin.auth.service.UserService;
import id.ac.ui.cs.advprog.minjemin.item.model.Item;
import id.ac.ui.cs.advprog.minjemin.item.repository.ItemRepository;
import id.ac.ui.cs.advprog.minjemin.peminjaman.repository.PeminjamanRepository;
import id.ac.ui.cs.advprog.minjemin.peminjaman.util.PeminjamanCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PeminjamanServiceImplTest {

    private UserDTO userDto;
    private User user;
    private Item item;
    private MinjeminUserDetails minjeminUserDetails;

    @Mock
    PeminjamanRepository peminjamanRepository;

    @Mock
    ItemRepository itemRepository;

    @Mock
    UserService userService;

    @Mock
    SecurityService securityService;

    @Mock
    PeminjamanCreator peminjamanCreator;

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
    }

    @Test
    void testCreatePeminjamanValid() {
        when(securityService.findLoggedInUserDetails()).thenReturn(minjeminUserDetails);
        when(userService.findByUsername("blax")).thenReturn(user);
        when(itemRepository.getById("item-1")).thenReturn(item);

        var res = peminjamanService.createPeminjaman("item-1", "2022-09-09", "2022-09-11");
        assertEquals("Barang berhasil dipinjam", res);

        item.setStatus("tersedia");
        res = peminjamanService.createPeminjaman("item-1", "2022-09-09", "2022-10-09");
        assertEquals("Barang berhasil dipinjam", res);

        item.setStatus("tersedia");
        res = peminjamanService.createPeminjaman("item-1", "2022-09-09", "2023-10-09");
        assertEquals("Barang berhasil dipinjam", res);
    }

    @Test
    void testCreateItemNoGapBetweenDate(){
        when(securityService.findLoggedInUserDetails()).thenReturn(minjeminUserDetails);
        when(userService.findByUsername("blax")).thenReturn(user);

        var res = peminjamanService.createPeminjaman("item-1", "2022-09-09", "2022-09-09");
        assertEquals("Tanggal tidak valid", res);
    }

    @Test
    void testCreateItemForPast(){
        when(securityService.findLoggedInUserDetails()).thenReturn(minjeminUserDetails);
        when(userService.findByUsername("blax")).thenReturn(user);

        var res = peminjamanService.createPeminjaman("item-1", "2022-09-09", "2021-09-19");
        assertEquals("Tanggal tidak valid", res);
    }

    @Test
    void testCreateItemStatusBarangSudahTidakTersedia() {
        when(securityService.findLoggedInUserDetails()).thenReturn(minjeminUserDetails);
        when(userService.findByUsername("blax")).thenReturn(user);
        when(itemRepository.getById("item-1")).thenReturn(item);

        var res = peminjamanService.createPeminjaman("item-1", "2022-09-09", "2022-09-11");
        assertEquals("Barang berhasil dipinjam", res);

        res = peminjamanService.createPeminjaman("item-1", "2022-11-09", "2022-11-11");
        assertEquals("Status barang sudah tidak tersedia", res);
    }
}
