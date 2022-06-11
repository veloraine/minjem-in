package id.ac.ui.cs.advprog.minjemin.item.service;

import id.ac.ui.cs.advprog.minjemin.item.model.Item;
import id.ac.ui.cs.advprog.minjemin.item.repository.ItemRepository;
import id.ac.ui.cs.advprog.minjemin.item.model.ItemDTO;
import id.ac.ui.cs.advprog.minjemin.item.util.ImageProcessor;
import id.ac.ui.cs.advprog.minjemin.peminjaman.model.Peminjaman;
import id.ac.ui.cs.advprog.minjemin.peminjaman.repository.PeminjamanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    PeminjamanRepository peminjamanRepository;

    @Override
    public Item getItemById(String id) {
        List<Item> tmp = itemRepository.findAll();
        if (!tmp.isEmpty())
            return itemRepository.findItemById(id);
        return null;
    }

    @Override
    public ItemDTO getItemDTOById(String id) {
        List<ItemDTO> tmp = getItems();
        for (ItemDTO item:tmp) {
            if (item.getId().equals(id)) return item;
        }

        return null;
    }

    @Override
    public Item createItem(String name, String desc, int harga, MultipartFile file) throws IOException {
        var imageProcessor = ImageProcessor.getInstance();
        byte[] imageBytes = imageProcessor.convertToByte(file);

        var itemBuilder = Item.builder();
        var itemName = itemBuilder.name(name);
        var itemDesc = itemName.desc(desc);
        var itemHarga = itemDesc.harga(harga);
        var itemStatus = itemHarga.status("tersedia");
        var itemImage = itemStatus.profilePic(imageBytes);

        var item = itemImage.build();
        return itemRepository.save(item);
    }

    @Override
    public void updateItem(String id, String name, String desc, int harga, MultipartFile file) throws IOException {
        var tmp = itemRepository.findAll();
        var item = itemRepository.findItemById(id);
        if (!tmp.isEmpty()) {
            item.setName(name);
            item.setDesc(desc);
            item.setHarga(harga);
        }

        if (file.isEmpty()) {
            item.setProfilePic(item.getProfilePic());
            itemRepository.save(item);
        } else {
            var imageProcessor = ImageProcessor.getInstance();
            byte[] imageBytes = imageProcessor.convertToByte(file);

            item.setProfilePic(imageBytes);
            itemRepository.save(item);
        }
    }

    @Override
    public void updateStatusItem(String id, int code) {
        var item = itemRepository.getById(id);
        if (code == 1) {
            item.setStatus("tidak tersedia");
        } else if (code == 2) {
            item.setStatus("tersedia");
        }
    }

    @Override
    public List<ItemDTO> getItems(){
        var imageProcessor = ImageProcessor.getInstance();
        List<Item> items = itemRepository.findAll();
        List<ItemDTO> itemDTO = new ArrayList<>();
        for (Item item: items) {
            byte[] profileByte = item.getProfilePic();
            var encode64 = imageProcessor.generateStringImage(profileByte);
            var objectDTO = new ItemDTO(item.getId(), item.getName(), item.getDesc(), item.getHarga(), item.getStatus(), encode64);
            itemDTO.add(objectDTO);
        }
        return itemDTO;
    }

    @Override
    public void deleteItem(String id) {
        var item = itemRepository.getById(id);
        itemRepository.delete(item);
        List<Peminjaman> peminjaman = peminjamanRepository.findAllByItemId(id);
        for (Peminjaman i : peminjaman) {
            peminjamanRepository.delete(i);
        }
    }
}
