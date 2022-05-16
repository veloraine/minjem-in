package id.ac.ui.cs.advprog.minjemin.peminjaman.service;

import id.ac.ui.cs.advprog.minjemin.auth.model.User;
import id.ac.ui.cs.advprog.minjemin.auth.model.UserDTO;
import id.ac.ui.cs.advprog.minjemin.auth.security.MinjeminUserDetails;
import id.ac.ui.cs.advprog.minjemin.auth.service.SecurityService;
import id.ac.ui.cs.advprog.minjemin.auth.service.UserService;
import id.ac.ui.cs.advprog.minjemin.item.model.Item;
import id.ac.ui.cs.advprog.minjemin.item.model.ItemDTO;
import id.ac.ui.cs.advprog.minjemin.item.repository.ItemRepository;
import id.ac.ui.cs.advprog.minjemin.item.service.ItemService;
import id.ac.ui.cs.advprog.minjemin.peminjaman.model.Peminjaman;
import id.ac.ui.cs.advprog.minjemin.peminjaman.model.PeminjamanDTO;
import id.ac.ui.cs.advprog.minjemin.peminjaman.repository.PeminjamanRepository;
import id.ac.ui.cs.advprog.minjemin.peminjaman.util.PeminjamanCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PeminjamanServiceImplTest {

    private UserDTO userDto;
    private User user;
    private Item item;
    private MinjeminUserDetails minjeminUserDetails;

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

    @Test
    void testGetAllPeminjamanMethod(){
        List<Peminjaman> peminjamanList = new ArrayList<>();
        peminjamanList.add(new Peminjaman("pinjam-1", "user-1", "item-1", "2002-01-01", "2003-01-01", "diterima", "belum dibayar"));
        when(peminjamanRepository.findAll()).thenReturn(peminjamanList);
        List<PeminjamanDTO> peminjamanDTOResult = peminjamanService.getAllPeminjaman();
        assertEquals(1, peminjamanDTOResult.size());
    }

    @Test
    void testGetPeminjamanByItemId(){
        List<Peminjaman> peminjamanList = new ArrayList<>();
        Peminjaman pinjam1 = new Peminjaman("pinjam-1", "user-1", "item-1",
                "2002-01-01", "2003-01-01", "diterima", "belum dibayar");
        peminjamanList.add(pinjam1);
        when(peminjamanService.getPeminjamanByItemId("pinjam-1")).thenReturn(pinjam1);
        Peminjaman chosen = peminjamanService.getPeminjamanByItemId("pinjam-1");
        assertEquals(chosen.getId(), pinjam1.getId());
    }

    @Test
    void testTolakPeminjaman(){
        Peminjaman pinjam1 = new Peminjaman("pinjam-1", "user-1", "item-1",
                "2002-01-01", "2003-01-01", "menunggu", "belum dibayar");
        when(peminjamanRepository.findPeminjamanById("pinjam-1")).thenReturn(pinjam1);
        peminjamanService.tolakPeminjaman("pinjam-1");
        assertEquals("Ditolak", pinjam1.getStatus());


    }

    @Test
    void testTerimaPeminjaman(){
        Peminjaman pinjam1 = new Peminjaman("pinjam-1", "user-1", "item-1",
                "2002-01-01", "2003-01-01", "menunggu", "belum dibayar");
        when(peminjamanRepository.findPeminjamanById("pinjam-1")).thenReturn(pinjam1);
        peminjamanService.terimaPeminjaman("pinjam-1");
        assertEquals("Diterima", pinjam1.getStatus());
    }
}
