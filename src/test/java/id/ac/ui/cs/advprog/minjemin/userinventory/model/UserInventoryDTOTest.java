package id.ac.ui.cs.advprog.minjemin.userinventory.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UserInventoryDTOTest {
    private Class<?> userInventoryDTOClass;

    @BeforeEach
    void setUp() throws Exception{
        userInventoryDTOClass = Class.forName("id.ac.ui.cs.advprog.minjemin.userinventory.model.UserInventoryDTO");
    }

    @Test
    void testUserInventoryDTOModifier() {
        int classModifiers = userInventoryDTOClass.getModifiers();
        assertTrue(Modifier.isPublic(classModifiers));
    }

    @Test
    void testMakeAnUserInventoryDTO() {
        var userInventoryDTO = new UserInventoryDTO("nama", "base64", "desc", "tanggalMulai", "tanggalSelesai", 5, 5, 5, "itemId", "idPeminjam", "peminjamanId", "statusPembayaran", "statusPeminjaman");
        assertEquals("nama", userInventoryDTO.getNama());
        assertEquals("base64", userInventoryDTO.getBase64());
        assertEquals("desc", userInventoryDTO.getDesc());
        assertEquals("tanggalMulai", userInventoryDTO.getTanggalMulai());
        assertEquals("tanggalSelesai", userInventoryDTO.getTanggalselesai());
        assertEquals(5, userInventoryDTO.getDurasi());
        assertEquals(5, userInventoryDTO.getBiaya());
        assertEquals(5, userInventoryDTO.getHarga());
        assertEquals("itemId", userInventoryDTO.getItemId());
        assertEquals("idPeminjam", userInventoryDTO.getIdPeminjam());
        assertEquals("peminjamanId", userInventoryDTO.getPeminjamanId());
        assertEquals("statusPembayaran", userInventoryDTO.getStatusPembayaran());
        assertEquals("statusPeminjaman", userInventoryDTO.getStatusPeminjaman());
    }

    @Test
    void testManipulateAnNoArgsDTO() {
        var userInventoryDTOTwo = new UserInventoryDTO();
        assertNull(userInventoryDTOTwo.getNama());
        userInventoryDTOTwo.setNama("Dragon Balls");
        assertEquals("Dragon Balls", userInventoryDTOTwo.getNama());
    }

}
