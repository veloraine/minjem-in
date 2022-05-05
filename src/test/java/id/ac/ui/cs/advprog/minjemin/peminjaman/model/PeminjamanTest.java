package id.ac.ui.cs.advprog.minjemin.peminjaman.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PeminjamanTest {
    private Class<?> peminjamanClass;

    @BeforeEach
    void setUp() throws Exception{
        peminjamanClass = Class.forName("id.ac.ui.cs.advprog.minjemin.peminjaman.model.Peminjaman");
    }

    @Test
    void testClassModifier() {
        int classModifiers = peminjamanClass.getModifiers();
        assertTrue(Modifier.isPublic(classModifiers));
    }

    @Test
    void testCreatePeminjamanAndCheckAttributes() {
        var peminjamanBuilder = Peminjaman.builder();
        var peminjamanValues = peminjamanBuilder
                .userId("user-1")
                .itemId("item-1")
                .tanggalMulai("2021-10-10")
                .tanggalSelesai("2021-10-19")
                .status("menunggu persetujuan")
                .statusPembayaran("belum dibayar");
        var peminjaman = peminjamanValues.build();
        peminjaman.setId("peminjaman-1");

        assertEquals("peminjaman-1", peminjaman.getId());
        assertEquals("user-1", peminjaman.getUserId());
        assertEquals("item-1", peminjaman.getItemId());
        assertEquals("2021-10-10", peminjaman.getTanggalMulai());
        assertEquals("2021-10-19", peminjaman.getTanggalSelesai());
        assertEquals("menunggu persetujuan", peminjaman.getStatus());
        assertEquals("belum dibayar", peminjaman.getStatusPembayaran());
    }

    @Test
    void testNoArgsPeminjaman() {
        
        var peminjamanNoArgs = new Peminjaman();
        assertNull(peminjamanNoArgs.getStatus());
    }
}
