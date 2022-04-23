package id.ac.ui.cs.advprog.minjemin.item.repository;

import id.ac.ui.cs.advprog.minjemin.item.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {
}

