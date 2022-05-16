package id.ac.ui.cs.advprog.minjemin.userInventory.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class UserInventoryTest {
    private Class<?> userInventoryServiceClass;

    @BeforeEach
    void setUp() throws Exception {
        userInventoryServiceClass = Class.forName("id.ac.ui.cs.advprog.minjemin.userInventory.service.UserInventoryService");
    }

    @Test
    void testGetUserInventoryMethod() throws NoSuchMethodException{
        Method showUserInventoryMethod = userInventoryServiceClass.getMethod("showUserInventory");
        int modifier = showUserInventoryMethod.getModifiers();

        assertTrue(Modifier.isAbstract(modifier));
    }
}