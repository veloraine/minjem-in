package id.ac.ui.cs.advprog.minjemin.item.service;

import id.ac.ui.cs.advprog.minjemin.item.model.Item;
import id.ac.ui.cs.advprog.minjemin.item.model.ItemDTO;
import id.ac.ui.cs.advprog.minjemin.item.repository.ItemRepository;
import id.ac.ui.cs.advprog.minjemin.item.util.ImageProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {
    Item item;
    List<Item> items;

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

        items = new ArrayList<>();
    }

    @Test
    void testGetItemById() {
        List<Item> itemList = new ArrayList<>();
        itemList.add(item);
        when(itemRepository.findAll()).thenReturn(itemList);
        lenient().when(itemService.getItemById("1")).thenReturn(item);
        Item chosenItem = itemService.getItemById("1");
        assertEquals(chosenItem.getId(), item.getId());
    }

    @Test
    void testGetItemByIdWhenEmpty() {
        List<Item> itemList = new ArrayList<>();
        when(itemRepository.findAll()).thenReturn(itemList);
        Item chosenItem = itemService.getItemById("1");
        assertNull(chosenItem);
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
    void testUpdateItemMethod() throws Exception {
        byte[] scouter = "scouter".getBytes();
        MockMultipartFile file = new MockMultipartFile(
                "file", "Scouter.jpeg", "image/jpeg", scouter);

        List<Item> itemList = new ArrayList<>();
        itemList.add(item);
        when(itemRepository.findAll()).thenReturn(itemList);

        when(itemRepository.findItemById("0")).thenReturn(item);

        String pastname = item.getName();
        String newName = "baru";
        itemService.updateItem("0", newName, item.getDesc(), item.getHarga(), file);
        assertNotEquals(item.getName(), pastname);
    }

    @Test
    void testUpdateItemMethodWhenEmpty() throws Exception {
        byte[] scouter = "scouter".getBytes();
        MockMultipartFile file = new MockMultipartFile(
                "file", "Scouter.jpeg", "image/jpeg", scouter);

        List<Item> itemList = new ArrayList<>();
        when(itemRepository.findAll()).thenReturn(itemList);

        when(itemRepository.findItemById("0")).thenReturn(item);

        String pastname = item.getName();
        String newName = "baru";
        itemService.updateItem("0", newName, item.getDesc(), item.getHarga(), file);
        assertEquals(item.getName(), pastname);
    }

    @Test
    void testUpdateItemMethodWhenFileEmpty() throws Exception {
        byte[] scouter = null;
        MockMultipartFile file = new MockMultipartFile(
                "file", "Scouter.jpeg", "image/jpeg", scouter);
        List<Item> itemList = new ArrayList<>();
        itemList.add(item);
        when(itemRepository.findAll()).thenReturn(itemList);

        when(itemRepository.findItemById("0")).thenReturn(item);

        String pastname = item.getName();
        byte[] pastPic = item.getProfilePic();
        String newName = "baru";
        itemService.updateItem("0", newName, item.getDesc(), item.getHarga(), file);
        assertNotEquals(item.getName(), pastname);
        assertEquals(item.getProfilePic(), pastPic);
    }

    @Test
    void testUpdateStatusItemWhen1(){
        when(itemRepository.getById("item-1")).thenReturn(item);
        itemService.updateStatusItem("item-1",1);
        assertEquals("tidak tersedia", item.getStatus());
    }

    @Test
    void testUpdateStatusItemWhen2(){
        when(itemRepository.getById("item-1")).thenReturn(item);
        itemService.updateStatusItem("item-1",2);
        assertEquals("tersedia", item.getStatus());
    }


    @Test
    void testGetItemsMethod(){
        List<Item> itemList = new ArrayList<>();
        itemList.add(item);
        when(itemRepository.findAll()).thenReturn(itemList);
        List<ItemDTO> itemDTOResult = itemService.getItems();
        assertEquals(1, itemDTOResult.size());
    }

    @Test
    void testDeleteItem() throws Exception {
        byte[] scouter = "scouter".getBytes();
        MockMultipartFile file = new MockMultipartFile(
                "file", "Scouter.jpeg", "image/jpeg", scouter);
        itemService.createItem(item.getName(), item.getDesc(), item.getHarga(), file);
        lenient().when(itemRepository.getById("0")).thenReturn(item);
        itemService.deleteItem("0");
        lenient().when(itemRepository.getById("0")).thenReturn(null);
        assertNull(itemRepository.getById("0"));
    }

    @Test
    void testDeleteNonExistentEditorById() throws Exception{
        byte[] scouter = "scouter".getBytes();
        MockMultipartFile file = new MockMultipartFile(
                "file", "Scouter.jpeg", "image/jpeg", scouter);
        itemService.createItem(item.getName(), item.getDesc(), item.getHarga(), file);
        lenient().when(itemRepository.getById("0")).thenReturn(null);
        itemService.deleteItem("0");
        assertNull(itemRepository.getById("0"));
    }

    @Test
    void testGetItemObjectMethod(){
        List<Item> itemList = new ArrayList<>();
        itemList.add(item);
        when(itemRepository.findAll()).thenReturn(itemList);
        lenient().when(itemService.getItemById("1")).thenReturn(item);
        Item chosenItem = itemService.getItemById("1");
        assertEquals(chosenItem.getId(), item.getId());
    }
}
