package id.ac.ui.cs.advprog.minjemin.item.model;

import id.ac.ui.cs.advprog.minjemin.item.model.ItemDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ItemDTOTest {
    private Class<?> itemDTOClass;

    @BeforeEach
    void setUp() throws Exception{
        itemDTOClass = Class.forName("id.ac.ui.cs.advprog.minjemin.item.model.ItemDTO");
    }

    @Test
    void testItemDTOModifier() {
        int classModifiers = itemDTOClass.getModifiers();
        assertTrue(Modifier.isPublic(classModifiers));
    }

    @Test
    void testMakeAnItemDTO() {
        var itemDTOOne = new ItemDTO("1", "item1", "an item made of steel", 500, "clankclank");
        assertEquals("item1", itemDTOOne.getName());
        assertEquals("an item made of steel", itemDTOOne.getDesc());
        assertEquals(500, itemDTOOne.getHarga());
        assertEquals("clankclank", itemDTOOne.getBase64Image());
    }

    @Test
    void testManipulateAnNoArgsDTO() {
        var itemDTOTwo = new ItemDTO();
        assertNull(itemDTOTwo.getName());
        itemDTOTwo.setName("Dragon Balls");
        assertEquals("Dragon Balls", itemDTOTwo.getName());
    }

}
