package id.ac.ui.cs.advprog.minjemin.item.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {
    private Class<?> itemServiceClass;

    @BeforeEach
    void setUp() throws Exception {
        itemServiceClass = Class.forName("id.ac.ui.cs.advprog.minjemin.item.service.ItemService");
    }

    @Test
    void testCreateItemMethod() throws NoSuchMethodException{
        Method createItemMethod = itemServiceClass.getDeclaredMethod(
                "createItem", String.class, String.class, int.class, MultipartFile.class);
        int modifier = createItemMethod.getModifiers();

        assertTrue(Modifier.isAbstract(modifier));

    }

    @Test
    void testGetItemsMethod() throws NoSuchMethodException{
        Method getItemsMethod = itemServiceClass.getMethod("getItems");
        int modifier = getItemsMethod.getModifiers();

        assertTrue(Modifier.isAbstract(modifier));
    }
}
