package id.ac.ui.cs.advprog.minjemin.userInventory.controller;

import id.ac.ui.cs.advprog.minjemin.auth.model.User;
import id.ac.ui.cs.advprog.minjemin.auth.model.UserDTO;
import id.ac.ui.cs.advprog.minjemin.auth.security.MinjeminUserDetails;
import id.ac.ui.cs.advprog.minjemin.auth.service.MinjeminUserDetailsService;
import id.ac.ui.cs.advprog.minjemin.auth.service.SecurityService;
import id.ac.ui.cs.advprog.minjemin.userInventory.service.UserInventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = UserInventoryController.class)
public class UserInventoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private UserDTO userDto;
    private User user;
    private MinjeminUserDetails minjeminUserDetails;

    @MockBean
    private MinjeminUserDetailsService minjeminUserDetailsService;

    @MockBean
    UserInventoryService userInventoryService;

    @MockBean
    private SecurityService securityService;

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
        mockMvc.perform(get("/user-inventory"))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("showUserInventory"))
                .andExpect(model().attributeDoesNotExist())
                .andExpect(view().name("user-inventory/show"));
    }
}
