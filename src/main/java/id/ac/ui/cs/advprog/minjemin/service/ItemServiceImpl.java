package id.ac.ui.cs.advprog.minjemin.service;

import id.ac.ui.cs.advprog.minjemin.model.Item;
import id.ac.ui.cs.advprog.minjemin.model.ItemDTO;
import id.ac.ui.cs.advprog.minjemin.repository.ItemRepository;
import id.ac.ui.cs.advprog.minjemin.util.ImageProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemRepository itemRepository;

    @Override
    public List<ItemDTO> getItems(){
        var imageProcessor = ImageProcessor.getInstance();
        List<Item> items = itemRepository.findAll();
        List<ItemDTO> itemDTO = new ArrayList<>();
        for (Item item: items) {
            byte[] profileByte = item.getProfilePic();
            String encode64 = imageProcessor.generateStringImage(profileByte);
            var objectDTO = new ItemDTO(item.getName(), item.getDesc(), item.getHarga(), encode64);
            itemDTO.add(objectDTO);
        }
        return itemDTO;
    }
}
