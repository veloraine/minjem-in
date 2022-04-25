package id.ac.ui.cs.advprog.minjemin.auth.service;

import id.ac.ui.cs.advprog.minjemin.auth.exception.UnmatchPasswordException;
import id.ac.ui.cs.advprog.minjemin.auth.exception.UserAlreadyExistException;
import id.ac.ui.cs.advprog.minjemin.auth.exception.WhitespaceValueException;
import id.ac.ui.cs.advprog.minjemin.auth.model.User;
import id.ac.ui.cs.advprog.minjemin.auth.model.UserDTO;
import id.ac.ui.cs.advprog.minjemin.auth.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.NonUniqueResultException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDTO userDto;

    @BeforeEach
    public void setUp() {
        user = new User("user", "password1234", "USER");
        user.setPasswordConfirm("password1234");
        userDto = new UserDTO();
        userDto.setUsername("user");
        userDto.setPassword("password1234");
        userDto.setRoles("USER");
    }

    @Test
    void register() {
        when(userRepository.save(user)).thenReturn(user);
        assertEquals(userService.register(user), user);
    }

    @Test
    void findByUsername() throws UsernameNotFoundException {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        assertEquals(userService.findByUsername(user.getUsername()), user);
    }

    @Test
    void findByUsernameNotFound() {
        when(userRepository.findByUsername(user.getUsername()+"x")).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userService.findByUsername(user.getUsername()+"x"));
    }

    @Test
    void testGetListUser() {
        List<User> users = Collections.singletonList(user);
        when(userRepository.findAll()).thenReturn(users);
        assertEquals(users, userService.getListUser());
    }

    @Test
    void testValidateNonUnique(){
        when(userRepository.findByUsername(any())).thenThrow(new NonUniqueResultException());
        assertThrows(UserAlreadyExistException.class, () -> userService.validate(userDto));
    }

    @Test
    void testValidateWhitespace(){
        userDto.setUsername("         ");
        userDto.setPassword("   ");
        assertThrows(WhitespaceValueException.class, () -> userService.validate(userDto));
    }

    @Test
    void testValidateUnmatchPass(){
        userDto.setPassword("ab");
        userDto.setPasswordConfirm("abcd");
        assertThrows(UnmatchPasswordException.class, () -> userService.validate(userDto));
    }

    @Test
    void testValidateSuccessfully() throws UserAlreadyExistException, UnmatchPasswordException, WhitespaceValueException {
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        userDto.setPassword(user.getPassword());
        userDto.setPasswordConfirm(user.getPassword());
        User validatedUserDTO = userService.validate(userDto);
        assertTrue(
                user.getUsername().equals(validatedUserDTO.getUsername())
        && user.getRoles().equals(validatedUserDTO.getRoles()));
    }

}
