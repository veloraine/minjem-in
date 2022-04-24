package id.ac.ui.cs.advprog.minjemin.item.controller;

import id.ac.ui.cs.advprog.minjemin.item.controller.ItemController;
import id.ac.ui.cs.advprog.minjemin.item.service.ItemServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ItemController.class)
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemServiceImpl itemService;

    @Test
    void whenGetAddItemReturnStatus200() throws Exception {
        mockMvc.perform(get("/admin/create"))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("createItem"))
                .andExpect(model().attributeDoesNotExist())
                .andExpect(view().name("items/add_item"));
    }

    @Test
    void whenPostAddItemShouldCallItemService() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "Figure.jpeg", "image/jpeg", "ryzafigure".getBytes());
        mockMvc.perform(multipart("/admin/create")
                .file(file)
                .param("name","Figurine Ryza")
                .param("desc", "ini adalah figurine terbaru Ryza")
                .param("harga", "50000"));

        verify(itemService, times(1)).createItem("Figurine Ryza", "ini adalah figurine terbaru Ryza", 50000, file);
    }

    @Test
    void whenPOstAddItemFailedShouldGoToExceptionBlock() throws Exception{
        MockMultipartFile fileGoku = new MockMultipartFile("file", "Goku.jpeg", "image/jpeg", "figurgoku".getBytes());
        when(mockMvc.perform(multipart("/admin/create")
                .file(fileGoku)
                .param("name","Figurine Goku")
                .param("desc", "kamehamehaaaaa!")
                .param("harga", "9000"))).thenThrow(new IOException());

        mockMvc.perform(multipart("/admin/create")
                .file(fileGoku)
                .param("name","Figurine Goku")
                .param("desc", "kamehamehaaaaa!")
                .param("harga", "9000"))
                .andExpect(status().is3xxRedirection())
                .andReturn();


    }
}
