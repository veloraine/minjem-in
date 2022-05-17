package id.ac.ui.cs.advprog.minjemin.item.service;

import id.ac.ui.cs.advprog.minjemin.item.model.Item;
import id.ac.ui.cs.advprog.minjemin.item.model.ItemDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ItemService {
    Item getItemById(String id);
    Item createItem(String name, String desc, int harga, MultipartFile file) throws IOException;
    void updateItem(String id, String name, String desc, int harga, MultipartFile file) throws IOException;
    void deleteItem(String id);
    List<ItemDTO> getItems();
    ItemDTO getItemObject(String id);
}
