package id.ac.ui.cs.advprog.minjemin.api;

import id.ac.ui.cs.advprog.minjemin.auth.service.MinjeminUserDetailsService;
import id.ac.ui.cs.advprog.minjemin.auth.service.SecurityService;
import id.ac.ui.cs.advprog.minjemin.peminjaman.model.Peminjaman;
import id.ac.ui.cs.advprog.minjemin.peminjaman.model.PeminjamanDetails;
import id.ac.ui.cs.advprog.minjemin.peminjaman.service.PeminjamanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = PeminjamanApi.class)
class PeminjamanApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SecurityService securityService;

    @MockBean
    private MinjeminUserDetailsService minjeminUserDetailsService;

    @MockBean
    private PeminjamanService peminjamanService;

    private PeminjamanDetails peminjaman;

    @BeforeEach
    void setUp(){
        peminjaman = new PeminjamanDetails(
                "pinjam-1",
                "user-1",
                "item-1",
                "2002-01-11",
                "2003-01-11",
                "menunggu persetujuan",
                "belum dibayar");
        peminjaman.setId("peminjaman-1");
    }

    @Test
    void testGetAllPeminjamanByUserId() throws Exception{
        List<PeminjamanDetails> peminjamanList = new ArrayList<>();
        peminjamanList.add(peminjaman);

        when(peminjamanService.getAllPeminjamanByUserId("user-1")).thenReturn(peminjamanList);
        mockMvc.perform(get("/api/user-1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value("peminjaman-1"))
                .andExpect(jsonPath("$[0].userId").value("user-1"))
                .andExpect(jsonPath("$[0].itemName").value("item-1"))
                .andExpect(jsonPath("$[0].tanggalMulai").value("2002-01-11"))
                .andExpect(jsonPath("$[0].tanggalSelesai").value("2003-01-11"))
                .andExpect(jsonPath("$[0].status").value("menunggu persetujuan"))
                .andExpect(jsonPath("$[0].statusPembayaran").value("belum dibayar"));

        verify(peminjamanService, times((1))).getAllPeminjamanByUserId("user-1");
    }

    @Test
    void testPayAnItem() throws Exception {
        when(peminjamanService.payPeminjaman("item-1")).thenReturn("Status barang berhasil diubah");
        mockMvc.perform(get("/api/pay/item-1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(peminjamanService, times(1)).payPeminjaman("item-1");
    }

}
