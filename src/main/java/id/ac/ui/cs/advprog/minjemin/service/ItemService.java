package id.ac.ui.cs.advprog.minjemin.service;

import id.ac.ui.cs.advprog.minjemin.model.Item;
import id.ac.ui.cs.advprog.minjemin.model.ItemDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ItemService {
    Item createItem(String name, String desc, int harga, MultipartFile file) throws IOException;
    List<ItemDTO> getItems();
}
