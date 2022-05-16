package id.ac.ui.cs.advprog.minjemin.auth.repository;

import id.ac.ui.cs.advprog.minjemin.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
