package id.ac.ui.cs.advprog.minjemin.userInventory.service;

import id.ac.ui.cs.advprog.minjemin.auth.model.User;
import id.ac.ui.cs.advprog.minjemin.auth.model.UserDTO;
import id.ac.ui.cs.advprog.minjemin.auth.repository.UserRepository;
import id.ac.ui.cs.advprog.minjemin.auth.security.MinjeminUserDetails;
import id.ac.ui.cs.advprog.minjemin.auth.service.MinjeminUserDetailsService;
import id.ac.ui.cs.advprog.minjemin.auth.service.SecurityService;
import id.ac.ui.cs.advprog.minjemin.auth.service.UserService;
import id.ac.ui.cs.advprog.minjemin.item.model.Item;
import id.ac.ui.cs.advprog.minjemin.item.repository.ItemRepository;
import id.ac.ui.cs.advprog.minjemin.peminjaman.model.Peminjaman;
import id.ac.ui.cs.advprog.minjemin.peminjaman.repository.PeminjamanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

class UserInventoryServiceImplTest {
    private Class<?> userInventoryServiceClass;

    private Item item;

    private UserDTO userDto;
    private User user;

    private MinjeminUserDetails minjeminUserDetails;

    private Peminjaman peminjamanDummy;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private SecurityService securityService;

    @Mock
    private MinjeminUserDetailsService minjeminUserDetailsService;

    @Mock
    PeminjamanRepository peminjamanRepository;

    @Mock
    UserRepository userRepository;


    @InjectMocks
    private UserInventoryServiceImpl userInventoryService;

    @Mock
    ItemRepository itemRepository;

    @BeforeEach
    void setUp() throws Exception {
        userInventoryServiceClass = Class.forName("id.ac.ui.cs.advprog.minjemin.userInventory.service.UserInventoryService");

        userDto = new UserDTO();
        userDto.setUsername("blax");
        userDto.setPassword("OKE123456789");
        userDto.setRoles("USER");
        userDto.setPasswordConfirm("OKE123456789");
        user = new User(userDto.getUsername(), userDto.getPassword(), userDto.getRoles());
        user.setId("user-1");
        user.setPasswordConfirm(userDto.getPasswordConfirm());
        minjeminUserDetails = new MinjeminUserDetails(user);

        item = Item.builder()
                .name("scouter")
                .desc("ini scouter")
                .harga(9000)
                .status("tersedia")
                .profilePic("scouter".getBytes())
                .build();
        item.setId("item-1");

        peminjamanDummy = new Peminjaman("pinjam-1", "user-1", "item-1",
                "2002-01-11", "2003-01-11", "menunggu persetujuan", "belum dibayar");
        peminjamanDummy.setId("peminjaman-1");
    }

    @Test
    void testGetUserInventoryMethod() throws NoSuchMethodException{
        Method showUserInventoryMethod = userInventoryServiceClass.getMethod("showUserInventory");
        int modifier = showUserInventoryMethod.getModifiers();

        assertTrue(Modifier.isAbstract(modifier));
    }

    @Test
    void testGetUserInventoryReturnTheCorrectUserInventory() throws Exception {
        List<Peminjaman> peminjamanUser = new ArrayList<>();
        peminjamanUser.add(peminjamanDummy);

        when(securityService.findLoggedInUserDetails()).thenReturn(minjeminUserDetails);
        when(userRepository.findByUsername("blax")).thenReturn(Optional.ofNullable(user));
        when(peminjamanRepository.findAllByUserId("user-1")).thenReturn(peminjamanUser);
        when(itemRepository.findItemById("item-1")).thenReturn(item);

        var res = userInventoryService.showUserInventory();
        assertEquals(res.get(0).getItemId(), "item-1");
    }
}