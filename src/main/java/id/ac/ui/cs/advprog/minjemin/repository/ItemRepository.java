package id.ac.ui.cs.advprog.minjemin.repository;

import id.ac.ui.cs.advprog.minjemin.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {
}

