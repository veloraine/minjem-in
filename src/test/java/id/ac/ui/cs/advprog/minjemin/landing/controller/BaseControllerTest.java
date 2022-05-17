package id.ac.ui.cs.advprog.minjemin.landing.controller;

import id.ac.ui.cs.advprog.minjemin.auth.service.MinjeminUserDetailsService;
import id.ac.ui.cs.advprog.minjemin.auth.service.SecurityService;
import id.ac.ui.cs.advprog.minjemin.item.model.Item;
import id.ac.ui.cs.advprog.minjemin.item.model.ItemDTO;
import id.ac.ui.cs.advprog.minjemin.item.service.ItemServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = BaseController.class)
class BaseControllerTest {

    ItemDTO item;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SecurityService securityService;

    @MockBean
    private MinjeminUserDetailsService minjeminUserDetailsService;

    @MockBean
    private ItemServiceImpl itemService;

    @BeforeEach
    void setUp() {
        item = ItemDTO.builder()
                .name("scouter")
                .desc("ini scouter")
                .harga(9000)
                .status("tersedia")
                .build();
        item.setId("item-1");
        Mockito.reset(securityService, minjeminUserDetailsService);
    }

    @Test
    void whenGetHomepageURLShouldCallItemService() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("homepage"))
                .andExpect(model().attributeExists("items"))
                .andExpect(view().name("homepage"));

        verify(itemService, times(1)).getItems();
    }

    @Test
    void whenGetItemDetailReturnStatus200() throws Exception {
        when(itemService.getItemObject("9000")).thenReturn(item);

        mockMvc.perform(get("/items/detail/9000"))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("itemDetail"))
                .andExpect(model().attributeDoesNotExist())
                .andExpect(view().name("items/detail"));
    }

}
