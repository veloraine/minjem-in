package id.ac.ui.cs.advprog.minjemin.auth.service;

import id.ac.ui.cs.advprog.minjemin.auth.model.User;
import id.ac.ui.cs.advprog.minjemin.auth.repository.UserRepository;
import id.ac.ui.cs.advprog.minjemin.auth.security.MinjeminUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SecurityServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private Authentication auth;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @InjectMocks
    private SecurityServiceImpl securityService;
    @Mock
    private MinjeminUserDetailsService userDetailsService;

    private User user;
    private MinjeminUserDetails userDetails;


    @BeforeEach
    public void setUp() {
        user = new User("user", "password", "USER");
        userDetails = new MinjeminUserDetails(user);
    }

    @Test
    void findLoggedInUserDetailsNotLoggedIn() {
        lenient().when(auth.getPrincipal()).thenReturn("mock");
        SecurityContextHolder.getContext().setAuthentication(auth);
        assertNull(securityService.findLoggedInUserDetails());
    }

    @Test
    void findLoggedInUserDetails(){
        when(auth.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.getContext().setAuthentication(auth);
        assertEquals(userDetails, securityService.findLoggedInUserDetails());
    }

    @Test
    void testAutoLogin(){
        UsernamePasswordAuthenticationToken token = Mockito.mock(UsernamePasswordAuthenticationToken.class);
        when(userDetailsService.loadUserByUsername("user")).thenReturn(userDetails);
        when(authenticationManager.authenticate(any())).thenReturn(token);
        securityService.autoLogin("user", "b");
        assertInstanceOf(UsernamePasswordAuthenticationToken.class, SecurityContextHolder.getContext().getAuthentication());
    }
}
