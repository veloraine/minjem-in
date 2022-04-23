package id.ac.ui.cs.advprog.minjemin.item.service;

import id.ac.ui.cs.advprog.minjemin.item.model.Item;
import id.ac.ui.cs.advprog.minjemin.item.model.ItemDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ItemService {
    Item createItem(String name, String desc, int harga, MultipartFile file) throws IOException;
    List<ItemDTO> getItems();
}
