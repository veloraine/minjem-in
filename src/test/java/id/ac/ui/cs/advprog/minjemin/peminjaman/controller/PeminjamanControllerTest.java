package id.ac.ui.cs.advprog.minjemin.peminjaman.controller;

import id.ac.ui.cs.advprog.minjemin.auth.model.User;
import id.ac.ui.cs.advprog.minjemin.auth.model.UserDTO;
import id.ac.ui.cs.advprog.minjemin.auth.security.MinjeminUserDetails;
import id.ac.ui.cs.advprog.minjemin.auth.service.MinjeminUserDetailsService;
import id.ac.ui.cs.advprog.minjemin.auth.service.SecurityService;
import id.ac.ui.cs.advprog.minjemin.auth.service.UserService;
import id.ac.ui.cs.advprog.minjemin.item.model.Item;
import id.ac.ui.cs.advprog.minjemin.item.service.ItemService;
import id.ac.ui.cs.advprog.minjemin.peminjaman.service.PeminjamanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PeminjamanController.class)
class PeminjamanControllerTest {
    private UserDTO userDto;
    private User user;
    private Item item;
    private MinjeminUserDetails minjeminUserDetails;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SecurityService securityService;

    @MockBean
    private MinjeminUserDetailsService minjeminUserDetailsService;

    @MockBean
    private ItemService itemService;

    @MockBean
    private UserService userService;

    @MockBean
    private PeminjamanService peminjamanService;

    @BeforeEach
    void setUp() {
        userDto = new UserDTO();
        userDto.setUsername("blax");
        userDto.setPassword("OKE123456789");
        userDto.setRoles("USER");
        userDto.setPasswordConfirm("OKE123456789");
        user = new User(userDto.getUsername(), userDto.getPassword(), userDto.getRoles());
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
    void testRedirectPageWhenNoUserLoggedIn() throws Exception{
        mockMvc.perform(get("/peminjaman/pinjam/item-1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/auth/login"));
    }

    @Test
    void testPageWhenUserLoggedIn() throws Exception {
        when(securityService.findLoggedInUserDetails()).thenReturn(minjeminUserDetails);
        when(userService.findByUsername("blax")).thenReturn(user);
        when(itemService.getItemById("item-1")).thenReturn(item);
        mockMvc.perform(get("/peminjaman/pinjam/item-1"))
                .andExpect(status().isOk())
                .andExpect(view().name("peminjaman/pinjam"))
                .andExpect(model().attributeExists("item", "user"));
    }

    @Test
    void testWhenPostShouldCallPeminjamanService() throws Exception {
        mockMvc.perform(post("/peminjaman/pinjam/item-1")
                        .param("id", "item-1")
                        .param("mulai", "2022-09-09")
                        .param("selesai", "2022-09-11"))
                .andExpect(status().isOk());
        verify(peminjamanService, times(1)).createPeminjaman("item-1", "2022-09-09", "2022-09-11");


    }

    @Test
    void whenGetTerimaPinjamShouldFound() throws Exception {
        when(itemService.getItemById("9000")).thenReturn(item);
        mockMvc.perform(get("/peminjaman/terima/9000"))
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().methodName("terimaPinjam"))
                .andExpect(model().attributeDoesNotExist())
                .andExpect(redirectedUrl("/admin/tabel-pengajuan/"));
    }

    @Test
    void whenGetTolakPinjamShouldFound() throws Exception {
        when(itemService.getItemById("9000")).thenReturn(item);
        mockMvc.perform(get("/peminjaman/tolak/9000"))
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().methodName("tolakPinjam"))
                .andExpect(model().attributeDoesNotExist())
                .andExpect(redirectedUrl("/admin/tabel-pengajuan/"));
    }

    @Test
    void whenGetBatalPinjamShouldFound() throws Exception {
        when(itemService.getItemById("9000")).thenReturn(item);
        mockMvc.perform(get("/peminjaman/batal/9000"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().methodName("batalPinjam"))
                .andExpect(model().attributeDoesNotExist());
    }
}
