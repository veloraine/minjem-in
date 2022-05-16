package id.ac.ui.cs.advprog.minjemin.item.model;

import id.ac.ui.cs.advprog.minjemin.item.model.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class ItemTest {
    private Class<?> itemClass;

    @BeforeEach
    void setUp() throws Exception{
        itemClass = Class.forName("id.ac.ui.cs.advprog.minjemin.item.model.Item");
    }

    @Test
    void testItemDTOModifier() {
        int classModifiers = itemClass.getModifiers();
        assertTrue(Modifier.isPublic(classModifiers));
    }

    @Test
    void testMakeAnItem(){
        var item = Item.builder()
                .name("scouter")
                .desc("ini scouter")
                .harga(9000)
                .status("tersedia")
                .profilePic("scouter".getBytes())
                .build();
        item.setId("item-1");
        item.setIdPinjaman("peminjaman-1");

        assertEquals("item-1", item.getId());
        assertEquals("scouter", item.getName());
        assertEquals(9000, item.getHarga());
        assertEquals("tersedia", item.getStatus());
        assertEquals("peminjaman-1", item.getIdPinjaman());

    }
}
