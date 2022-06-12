package id.ac.ui.cs.advprog.minjemin.notifikasi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class NotifikasiServiceTest {
    private Class<?> notifikasiServiceClass;

    @BeforeEach
    void setUp() throws Exception {
        notifikasiServiceClass = Class.forName("id.ac.ui.cs.advprog.minjemin.notifikasi.service.NotifikasiService");
    }

    @Test
    void testShowUserInventoryService() throws NoSuchMethodException {
        Method peminjamanDeadlineMethod = notifikasiServiceClass.getDeclaredMethod(
                "peminjamanDeadline");
        int modifier = peminjamanDeadlineMethod.getModifiers();

        assertTrue(Modifier.isAbstract(modifier));
    }
}
