package id.ac.ui.cs.advprog.minjemin.userinventory.service;

import id.ac.ui.cs.advprog.minjemin.userinventory.model.UserInventoryDTO;

import java.text.ParseException;
import java.util.List;

public interface UserInventoryService {
    List<UserInventoryDTO> showUserInventory() throws ParseException;
}
