package id.ac.ui.cs.advprog.minjemin.userinventory.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class UserInventoryServiceTest {
    private Class<?> userInventoryServiceClass;

    @BeforeEach
    void setUp() throws Exception {
        userInventoryServiceClass = Class.forName("id.ac.ui.cs.advprog.minjemin.userinventory.service.UserInventoryService");
    }

    @Test
    void testShowUserInventoryService() throws NoSuchMethodException {
        Method showUserInventoryMethod = userInventoryServiceClass.getDeclaredMethod(
                "showUserInventory");
        int modifier = showUserInventoryMethod.getModifiers();

        assertTrue(Modifier.isAbstract(modifier));
    }
}
