package id.ac.ui.cs.advprog.minjemin.pengembalian.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class PengembalianServiceTest {
    private Class<?> pengembalianServiceClass;

    @BeforeEach
    void setUp() throws Exception {
        pengembalianServiceClass = Class.forName("id.ac.ui.cs.advprog.minjemin.pengembalian.service.PengembalianService");
    }

    @Test
    void testPengembalianMethod() throws NoSuchMethodException {
        Method pengembalianMethod = pengembalianServiceClass.getDeclaredMethod(
                "pengembalian", String.class);
        int modifier = pengembalianMethod.getModifiers();

        assertTrue(Modifier.isAbstract(modifier));
    }
}
