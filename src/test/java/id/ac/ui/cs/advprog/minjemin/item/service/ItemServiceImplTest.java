package id.ac.ui.cs.advprog.minjemin.item.service;

import id.ac.ui.cs.advprog.minjemin.item.model.Item;
import id.ac.ui.cs.advprog.minjemin.item.model.ItemDTO;
import id.ac.ui.cs.advprog.minjemin.item.repository.ItemRepository;
import id.ac.ui.cs.advprog.minjemin.item.service.ItemServiceImpl;
import id.ac.ui.cs.advprog.minjemin.item.util.ImageProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {
    Item item;

    @Mock
    ItemRepository itemRepository;

    @Mock
    ImageProcessor imageProcessor;

    @InjectMocks
    ItemServiceImpl itemService;

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
    }

    @Test
    void testCreateItemMethod() throws Exception {
        byte[] scouter = "scouter".getBytes();
        MockMultipartFile file = new MockMultipartFile(
                "file", "Scouter.jpeg", "image/jpeg", scouter);

        lenient().when(imageProcessor.convertToByte(file)).thenReturn(scouter);
        lenient().when(itemRepository.save(item)).thenReturn(item);
        Item item = itemService.createItem("scouter", "ini scouter", 9000, file);
        assertNull(item);


    }

    @Test
    void testGetItemsMethod(){
        List<Item> itemList = new ArrayList<>();
        itemList.add(item);
        when(itemRepository.findAll()).thenReturn(itemList);
        List<ItemDTO> itemDTOResult = itemService.getItems();
        assertEquals(1, itemDTOResult.size());


    }
}
