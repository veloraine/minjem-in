package id.ac.ui.cs.advprog.minjemin.auth.service;

import id.ac.ui.cs.advprog.minjemin.auth.model.User;
import id.ac.ui.cs.advprog.minjemin.auth.repository.UserRepository;
import id.ac.ui.cs.advprog.minjemin.auth.security.MinjeminUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MinjeminUserDetailsServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MinjeminUserDetailsService userDetailsService;

    private User user;
    private MinjeminUserDetails userDetails;

    @BeforeEach
    public void setUp() {
        user = new User("username", "password", "USER");
        user.setPasswordConfirm("password");
        userDetails = new MinjeminUserDetails(user);
    }

    @Test
    void loadUserByUsername(){
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        UserDetails loaded = userDetailsService.loadUserByUsername(user.getUsername());
        assertEquals(loaded.getUsername(), userDetails.getUsername());
        assertEquals(loaded.getPassword(), userDetails.getPassword());
    }

    @Test
    void loadUserByUsernameNotFound(){
        String uname = user.getUsername()+"a";
        when(userRepository.findByUsername(uname)).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(uname));
    }

    @Test
    void userDetailsMethods(){
        MinjeminUserDetails userDetails = new MinjeminUserDetails();
        assertNull(userDetails.getAuthorities());
        assertNull(userDetails.getUsername());
        assertNull(userDetails.getPassword());
        assertTrue(userDetails.isAccountNonExpired());
        assertTrue(userDetails.isAccountNonLocked());
        assertTrue(userDetails.isCredentialsNonExpired());
        assertFalse(userDetails.isEnabled());
    }

}
