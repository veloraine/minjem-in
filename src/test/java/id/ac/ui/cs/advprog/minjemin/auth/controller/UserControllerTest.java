package id.ac.ui.cs.advprog.minjemin.auth.controller;

import id.ac.ui.cs.advprog.minjemin.auth.exception.UnmatchPasswordException;
import id.ac.ui.cs.advprog.minjemin.auth.exception.UserAlreadyExistException;
import id.ac.ui.cs.advprog.minjemin.auth.exception.WhitespaceValueException;
import id.ac.ui.cs.advprog.minjemin.auth.model.User;
import id.ac.ui.cs.advprog.minjemin.auth.model.UserDTO;
import id.ac.ui.cs.advprog.minjemin.auth.security.MinjeminUserDetails;
import id.ac.ui.cs.advprog.minjemin.auth.service.MinjeminUserDetailsService;
import id.ac.ui.cs.advprog.minjemin.auth.service.SecurityService;
import id.ac.ui.cs.advprog.minjemin.auth.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private SecurityService securityService;

    @MockBean
    private MinjeminUserDetailsService minjeminUserDetailsService;

    private UserDTO userDto;
    private User user;

    @BeforeEach
    public void setUp() {
        userDto = new UserDTO();
        userDto.setUsername("lulusadpro");
        userDto.setPassword("AQWQ12312121");
        userDto.setRoles("USER");
        userDto.setPasswordConfirm("AQWQ12312121");
        user = new User(userDto.getUsername(), userDto.getPassword(), userDto.getRoles());
        user.setPasswordConfirm(userDto.getPasswordConfirm());
    }

    @Test
    void testUserDetails() throws Exception {
        when(securityService.findLoggedInUserDetails()).thenReturn(new MinjeminUserDetails(user));
        mockMvc.perform(get("/auth/userDetail")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        
    }

    @Test
    void testGetListUser() throws Exception {
        mockMvc.perform(get("/auth/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testRegisterSucessfully() throws Exception {
        userDto.setUsername("lulusadpro");
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", userDto.getUsername())
                .sessionAttr("user", new User()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void testRegisterView() throws Exception {
        mockMvc.perform(get("/auth/register"))
                .andExpect(view().name("auth/register"));
    }

    @Test void testLoginMethod() throws Exception {
        mockMvc.perform(get("/auth/login"))
                .andExpect(view().name("auth/login"));
    }

    @Test
    void testRegisterUserAlreadyExist() throws Exception {
        when(userService.validate(any(UserDTO.class))).thenThrow(new UserAlreadyExistException());
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", userDto.getUsername())
                .param("password", userDto.getPassword())
                .param("roles", userDto.getRoles())
                .sessionAttr("user", userDto));
    }

    @Test
    void testRegisterPasswordUnmatch() throws Exception {
        when(userService.validate(any(UserDTO.class))).thenThrow(new UnmatchPasswordException());
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", userDto.getUsername())
                .param("password", userDto.getPassword())
                .param("roles", userDto.getRoles())
                .sessionAttr("user", userDto));
    }

    @Test
    void testRegisterWhiteSpaceValue() throws Exception {
        when(userService.validate(any(UserDTO.class))).thenThrow(new WhitespaceValueException());
        mockMvc.perform(post("/signup/pembeli")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", userDto.getUsername())
                .param("password", userDto.getPassword())
                .param("roles", userDto.getRoles())
                .sessionAttr("user", userDto));
    }
}
