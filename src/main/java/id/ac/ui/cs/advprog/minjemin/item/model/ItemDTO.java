package id.ac.ui.cs.advprog.minjemin.item.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {
    String id;
    String name;
    String desc;
    int harga;
    String status;
    String base64Image;

}
