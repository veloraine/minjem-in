package id.ac.ui.cs.advprog.minjemin.controller;

import id.ac.ui.cs.advprog.minjemin.landing.controller.BaseController;
import id.ac.ui.cs.advprog.minjemin.item.service.ItemServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = BaseController.class)
class BaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemServiceImpl itemService;

    @Test
    void whenGetHomepageURLShouldCallItemService() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("homepage"))
                .andExpect(model().attributeExists("items"))
                .andExpect(view().name("homepage"));

        verify(itemService, times(1)).getItems();
    }

}
