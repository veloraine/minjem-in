package id.ac.ui.cs.advprog.minjemin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {
    String name;
    String desc;
    int harga;
    String base64Image;

}
