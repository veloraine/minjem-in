package id.ac.ui.cs.advprog.minjemin.item.service;

import id.ac.ui.cs.advprog.minjemin.item.model.Item;
import id.ac.ui.cs.advprog.minjemin.item.model.ItemDTO;
import id.ac.ui.cs.advprog.minjemin.item.repository.ItemRepository;
import id.ac.ui.cs.advprog.minjemin.item.util.ImageProcessor;
import id.ac.ui.cs.advprog.minjemin.peminjaman.model.Peminjaman;
import id.ac.ui.cs.advprog.minjemin.peminjaman.repository.PeminjamanRepository;
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

    @Mock
    PeminjamanRepository peminjamanRepository;

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
    void testGetItemDTOById() {
        List<ItemDTO> itemList = new ArrayList<>();
        List<Item> items = new ArrayList<>();
        items.add(item);
        ItemDTO itemDTO = new ItemDTO("item-1", "koper", "bagus", 900, "tersedia", "koper.jpg");
        itemList.add(itemDTO);
        when(itemRepository.findAll()).thenReturn(items);
        ItemDTO chosenItem = itemService.getItemDTOById("item-1");
        assertEquals(chosenItem.getId(), item.getId());
    }

    @Test
    void testGetItemDTOByIdWhenEmpty() {
        List<ItemDTO> itemList = new ArrayList<>();
        List<Item> items = new ArrayList<>();
        items.add(item);
        ItemDTO itemDTO = new ItemDTO("1", "koper", "bagus", 900, "tersedia", "koper.jpg");
        itemList.add(itemDTO);
        when(itemRepository.findAll()).thenReturn(items);
        ItemDTO chosenItem = itemService.getItemDTOById("1");
        assertNull(chosenItem);
    }

    @Test
    void testGetItemDTOByIdWhenNoExist() {
        List<ItemDTO> itemList = new ArrayList<>();
        List<Item> items = new ArrayList<>();
        items.add(item);
        ItemDTO itemDTO = new ItemDTO("item-1", "koper", "bagus", 900, "tersedia", "koper.jpg");
        itemList.add(itemDTO);
        when(itemRepository.findAll()).thenReturn(items);
        ItemDTO chosenItem = itemService.getItemDTOById("item-2");
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
        Peminjaman peminjaman1 = new Peminjaman("pinjam-1", "user-1", "item-1",
                "2002-01-01", "2003-01-01", "menunggu", "belum dibayar");

        List<Peminjaman> peminjamanList = new ArrayList<>();
        peminjamanList.add(peminjaman1);

        byte[] scouter = "scouter".getBytes();
        MockMultipartFile file = new MockMultipartFile(
                "file", "Scouter.jpeg", "image/jpeg", scouter);
        itemService.createItem(item.getName(), item.getDesc(), item.getHarga(), file);
        lenient().when(itemRepository.getById("0")).thenReturn(item);
        itemService.deleteItem("0");
        lenient().when(itemRepository.getById("0")).thenReturn(null);
        assertNull(itemRepository.getById("0"));

        lenient().when(peminjamanRepository.findAllByItemId("item-1")).thenReturn(peminjamanList);
        assertEquals(0, peminjamanRepository.findAll().size());
    }

    @Test
    void testDeleteNonExistent() throws Exception{
        List<Peminjaman> peminjamanList = new ArrayList<>();
        byte[] scouter = "scouter".getBytes();
        MockMultipartFile file = new MockMultipartFile(
                "file", "Scouter.jpeg", "image/jpeg", scouter);
        itemService.createItem(item.getName(), item.getDesc(), item.getHarga(), file);
        lenient().when(itemRepository.getById("0")).thenReturn(null);
        itemService.deleteItem("0");
        assertNull(itemRepository.getById("0"));
        lenient().when(peminjamanRepository.findAllByItemId("item-1")).thenReturn(peminjamanList);
    }

    @Test
    void testDeleteItemWhenNoDeletePeminjaman() throws Exception {
        List<Peminjaman> peminjamanList = new ArrayList<>();

        byte[] scouter = "scouter".getBytes();
        MockMultipartFile file = new MockMultipartFile(
                "file", "Scouter.jpeg", "image/jpeg", scouter);
        itemService.createItem(item.getName(), item.getDesc(), item.getHarga(), file);
        lenient().when(itemRepository.getById("0")).thenReturn(item);
        itemService.deleteItem("0");
        lenient().when(itemRepository.getById("0")).thenReturn(null);
        assertNull(itemRepository.getById("0"));

        lenient().when(peminjamanRepository.findAllByItemId("item-1")).thenReturn(peminjamanList);
        assertEquals(0, peminjamanRepository.findAll().size());
    }
}
