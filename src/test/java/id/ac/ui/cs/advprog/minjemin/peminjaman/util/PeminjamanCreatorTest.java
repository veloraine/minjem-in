package id.ac.ui.cs.advprog.minjemin.peminjaman.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class PeminjamanCreatorTest {
    PeminjamanCreator peminjamanCreator;

    @BeforeEach
    void setUp() {
        peminjamanCreator = PeminjamanCreator.getInstance();
    }

    @Test
    void testPinjamanCreatorIsSingleton() {
        var creatorA = PeminjamanCreator.getInstance();
        var creatorB = PeminjamanCreator.getInstance();
        assertEquals(creatorA, creatorB);
    }
}
