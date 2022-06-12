package id.ac.ui.cs.advprog.minjemin.userinventory.controller;

import id.ac.ui.cs.advprog.minjemin.auth.model.User;
import id.ac.ui.cs.advprog.minjemin.auth.model.UserDTO;
import id.ac.ui.cs.advprog.minjemin.auth.security.MinjeminUserDetails;
import id.ac.ui.cs.advprog.minjemin.auth.service.MinjeminUserDetailsService;
import id.ac.ui.cs.advprog.minjemin.auth.service.SecurityService;
import id.ac.ui.cs.advprog.minjemin.auth.service.UserService;
import id.ac.ui.cs.advprog.minjemin.userinventory.service.UserInventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = UserInventoryController.class)
class UserInventoryControllerTest {
    private UserDTO userDto;
    private User user;

    private MinjeminUserDetails minjeminUserDetails;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SecurityService securityService;

    @MockBean
    private MinjeminUserDetailsService minjeminUserDetailsService;

    @MockBean
    private UserService userService;


    @MockBean
    private UserInventoryService userInventoryService;

    @BeforeEach
    void setUp() {
        userDto = new UserDTO();
        userDto.setUsername("blax");
        userDto.setPassword("OKE123456789");
        userDto.setRoles("USER");
        userDto.setPasswordConfirm("OKE123456789");
        user = new User(userDto.getUsername(), userDto.getPassword(), userDto.getRoles());
        user.setPasswordConfirm(userDto.getPasswordConfirm());
        minjeminUserDetails = new MinjeminUserDetails(user);
    }

    @Test
    void whenGetUserInventoryReturnStatus200() throws Exception {
        when(securityService.findLoggedInUserDetails()).thenReturn(minjeminUserDetails);
        when(userService.findByUsername("blax")).thenReturn(user);
        mockMvc.perform(get("/user-inventory"))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("showUserInventory"))
                .andExpect(model().attributeDoesNotExist())
                .andExpect(view().name("user-inventory/show"));
    }

    @Test
    void testWhenParseExceptionIsCalled() throws Exception {
        when(securityService.findLoggedInUserDetails()).thenReturn(minjeminUserDetails);
        when(userService.findByUsername("blax")).thenReturn(user);
        when(userInventoryService.showUserInventory()).thenThrow(new ParseException("HAha", 2));
        mockMvc.perform(get("/user-inventory"))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("showUserInventory"));
        verify(userInventoryService, times(1)).showUserInventory();
    }
}
