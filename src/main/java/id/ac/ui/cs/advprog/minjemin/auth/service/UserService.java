package id.ac.ui.cs.advprog.minjemin.auth.service;

import id.ac.ui.cs.advprog.minjemin.auth.exception.UnmatchPasswordException;
import id.ac.ui.cs.advprog.minjemin.auth.exception.UserAlreadyExistException;
import id.ac.ui.cs.advprog.minjemin.auth.exception.WhitespaceValueException;
import id.ac.ui.cs.advprog.minjemin.auth.model.User;
import id.ac.ui.cs.advprog.minjemin.auth.model.UserDTO;

public interface UserService {
    User findByUsername(String username);

    User register(User user);

    Iterable<User> getListUser();

    User validate(UserDTO userDto) throws UserAlreadyExistException, WhitespaceValueException, UnmatchPasswordException;
}
