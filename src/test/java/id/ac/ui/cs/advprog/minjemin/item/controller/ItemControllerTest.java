package id.ac.ui.cs.advprog.minjemin.item.controller;

import id.ac.ui.cs.advprog.minjemin.auth.service.MinjeminUserDetailsService;
import id.ac.ui.cs.advprog.minjemin.auth.service.SecurityService;
import id.ac.ui.cs.advprog.minjemin.auth.service.UserService;
import id.ac.ui.cs.advprog.minjemin.item.model.Item;
import id.ac.ui.cs.advprog.minjemin.item.service.ItemServiceImpl;
import id.ac.ui.cs.advprog.minjemin.peminjaman.service.PeminjamanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = ItemController.class)
class ItemControllerTest {
    Item item;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SecurityService securityService;

    @MockBean
    private MinjeminUserDetailsService minjeminUserDetailsService;

    @MockBean
    private ItemServiceImpl itemService;

    @MockBean
    private UserService userService;

    @MockBean
    private PeminjamanService peminjamanService;

    @BeforeEach
    void setUp() {
        item = Item.builder()
                .name("scouter")
                .desc("ini scouter")
                .harga(9000)
                .status("tersedia")
                .profilePic("scouter".getBytes())
                .build();
        item.setId("item-1");
        Mockito.reset(securityService, minjeminUserDetailsService);
    }

    @Test
    void whenGetAdminReturnStatus200() throws Exception {
        mockMvc.perform(get("/admin/"))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("dashboardAdmin"))
                .andExpect(model().attributeDoesNotExist())
                .andExpect(view().name("admin/dashboard"));
    }

    @Test
    void whenGetTabelPengajuanReturnStatus200() throws Exception {
        mockMvc.perform(get("/admin/tabel-pengajuan"))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("tabelPengajuan"))
                .andExpect(model().attributeDoesNotExist())
                .andExpect(view().name("admin/tabel_pengajuan"));
    }

    @Test
    void whenGetAddItemReturnStatus200() throws Exception {
        mockMvc.perform(get("/admin/create"))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("createItem"))
                .andExpect(model().attributeDoesNotExist())
                .andExpect(view().name("items/add_item"));
    }

    @Test
    void whenPostAddItemShouldCallItemService() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "Figure.jpeg", "image/jpeg", "ryzafigure".getBytes());
        mockMvc.perform(multipart("/admin/create")
                .file(file)
                .param("name","Figurine Ryza")
                .param("desc", "ini adalah figurine terbaru Ryza")
                .param("harga", "50000"));

        verify(itemService, times(1)).createItem("Figurine Ryza", "ini adalah figurine terbaru Ryza", 50000, file);
    }

    @Test
    void whenPostAddItemFailedShouldGoToExceptionBlock() throws Exception{
        MockMultipartFile fileGoku = new MockMultipartFile("file", "Goku.jpeg", "image/jpeg", "figurgoku".getBytes());
        when(mockMvc.perform(multipart("/admin/create")
                .file(fileGoku)
                .param("name","Figurine Goku")
                .param("desc", "kamehamehaaaaa!")
                .param("harga", "9000"))).thenThrow(new IOException());

        mockMvc.perform(multipart("/admin/create")
                .file(fileGoku)
                .param("name","Figurine Goku")
                .param("desc", "kamehamehaaaaa!")
                .param("harga", "9000"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
    }

    @Test
    void whenGetUpdateItemReturnStatus200() throws Exception {
        when(itemService.getItemById("9000")).thenReturn(item);

        mockMvc.perform(get("/admin/update/9000"))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("updateItem"))
                .andExpect(model().attributeDoesNotExist())
                .andExpect(view().name("items/update"));
    }

    @Test
    void whenPostUpdateItemShouldCallItemService() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "Figure.jpeg", "image/jpeg", "ryzafigure".getBytes());
        mockMvc.perform(multipart("/admin/update/9000")
                .file(file)
                .param("name","Figurine Ryza")
                .param("desc", "ini adalah figurine terbaru Ryza")
                .param("harga", "50000"));

        verify(itemService, times(1)).updateItem("9000","Figurine Ryza", "ini adalah figurine terbaru Ryza", 50000, file);
    }

    @Test
    void whenPostUpdateItemFailedShouldGoToExceptionBlock() throws Exception{
        MockMultipartFile fileGoku = new MockMultipartFile("file", "Goku.jpeg", "image/jpeg", "figurgoku".getBytes());
        when(mockMvc.perform(multipart("/admin/update/9000")
                .file(fileGoku)
                .param("name","Figurine Goku")
                .param("desc", "kamehamehaaaaa!")
                .param("harga", "9000"))).thenThrow(new IOException());

        mockMvc.perform(multipart("/admin/update/9000")
                        .file(fileGoku)
                        .param("name","Figurine Goku")
                        .param("desc", "kamehamehaaaaa!")
                        .param("harga", "9000"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
    }

    @Test
    void whenGetDeleteItemItShouldDeleteItem() throws Exception {
        mockMvc.perform(get("/admin/delete/12132123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().methodName("deleteItem"))
                .andExpect(model().attributeDoesNotExist())
                .andExpect(redirectedUrl("/admin/"));
    }

    @Test
    void whenGetTerimaPinjamanShouldFound() throws Exception {
        when(itemService.getItemById("9000")).thenReturn(item);
        mockMvc.perform(get("/admin/tabel-pengajuan/terimaPeminjaman/9000"))
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().methodName("terimaPinjam"))
                .andExpect(model().attributeDoesNotExist())
                .andExpect(redirectedUrl("/admin/tabel-pengajuan/"));
    }

    @Test
    void whenGetTolakPinjamanShouldFound() throws Exception {
        when(itemService.getItemById("9000")).thenReturn(item);
        mockMvc.perform(get("/admin/tabel-pengajuan/tolakPeminjaman/9000"))
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().methodName("tolakPinjam"))
                .andExpect(model().attributeDoesNotExist())
                .andExpect(redirectedUrl("/admin/tabel-pengajuan/"));
    }

}
