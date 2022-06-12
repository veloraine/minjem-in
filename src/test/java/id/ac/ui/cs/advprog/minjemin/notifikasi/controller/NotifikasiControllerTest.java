package id.ac.ui.cs.advprog.minjemin.notifikasi.controller;

import id.ac.ui.cs.advprog.minjemin.auth.model.User;
import id.ac.ui.cs.advprog.minjemin.auth.model.UserDTO;
import id.ac.ui.cs.advprog.minjemin.auth.security.MinjeminUserDetails;
import id.ac.ui.cs.advprog.minjemin.auth.service.MinjeminUserDetailsService;
import id.ac.ui.cs.advprog.minjemin.auth.service.SecurityService;
import id.ac.ui.cs.advprog.minjemin.auth.service.UserService;
import id.ac.ui.cs.advprog.minjemin.item.model.Item;
import id.ac.ui.cs.advprog.minjemin.item.service.ItemService;
import id.ac.ui.cs.advprog.minjemin.notifikasi.service.NotifikasiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;

import java.text.ParseException;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = NotifikasiController.class)
class NotifikasiControllerTest {
    private UserDTO userDto;
    private User user;
    private Item item;
    private MinjeminUserDetails minjeminUserDetails;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SecurityService securityService;

    @MockBean
    private MinjeminUserDetailsService minjeminUserDetailsService;

    @MockBean
    private ItemService itemService;

    @MockBean
    private UserService userService;

    @MockBean
    private NotifikasiService notifikasiService;

    @Test
    void testWhenGetShouldCallNotifikasiService() throws Exception {
        mockMvc.perform(get("/notifikasi"))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("showNotification"));
        verify(notifikasiService, times(1)).peminjamanDeadline();
    }

    @Test
    void testWhenParseExceptionIsCalled() throws Exception {
        when(notifikasiService.peminjamanDeadline()).thenThrow(new ParseException("HAha", 2));
        mockMvc.perform(get("/notifikasi"))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("showNotification"));
        verify(notifikasiService, times(1)).peminjamanDeadline();
    }
}
