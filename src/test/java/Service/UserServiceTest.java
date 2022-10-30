package Service;

import dao.KeyDAO;
import dao.UserDAO;
import dao_impl.KeyDAOImpl;
import dao_impl.UserDAOImpl;
import entity.Key;
import entity.User;
import org.apache.commons.codec.binary.Base64;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import service.UserService;
import service_impl.UserServiceImpl;
import util.AESUtil;
import util.enums.RoleName;

import javax.crypto.SecretKey;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest extends Mockito {
    private UserServiceImpl userService;
    private UserDAO userDAO;
    private KeyDAO keyDAO;

    @BeforeEach
    public void init() {
        userService = UserServiceImpl.getInstance();
        userDAO = mock(UserDAOImpl.class);
        keyDAO = mock(KeyDAOImpl.class);

        userService.setUserDAO(userDAO);
        userService.setKeyDAO(keyDAO);

//        try (MockedStatic<UserDAOImpl> userDAOMocked = mockStatic(UserDAOImpl.class)) {
//            userDAOMocked.when(UserDAOImpl::getInstance).thenReturn(userDAO);
//        }
    }

    @Test
    public void testUserInsertion() {
        User user = new User(0, "danichTest@gmail.com", "xxxxxxxx", "dan", "pro", RoleName.guest);

        when(userDAO.insertEntity(any(User.class))).thenReturn(user);
        when(keyDAO.insertEntity(any(Key.class))).thenReturn(true);

        assertTrue(userService.addUser("danichTest@gmail.com", "xxxxxxxx", "dan", "pro", RoleName.guest));
    }

    @Test
    public void testFindUser() {
        User user = new User(0, "danichTest@gmail.com", "xxxxxxxx", "dan", "pro", RoleName.guest);

        when(userDAO.getEntityByEmail("danichTest@gmail.com")).thenReturn(user);
        User foundUser = userService.getUser("danichTest@gmail.com");

        assertNotNull(foundUser);
        assertEquals(user.getEmail(), foundUser.getEmail());
    }

    @Test
    public void testAuthentication() {
        SecretKey secretKey = AESUtil.generateSecretKey();
        User user = new User(0, "danichTest@gmail.com", userService.encryptPassword(secretKey, "xxxxxxxx"), "dan", "pro", RoleName.guest);

        when(userDAO.getEntityByEmail("danichTest@gmail.com")).thenReturn(user);
        when(keyDAO.getEntityByUserId(anyInt())).thenReturn(new Key(0, new Base64().encodeToString(secretKey.getEncoded())));

        assertTrue(userService.authenticate("danichTest@gmail.com", "xxxxxxxx"));
    }

    @Test
    public void testFailAuthentication() {
        SecretKey secretKey = AESUtil.generateSecretKey();
        User user = new User(0, "danichTest@gmail.com", userService.encryptPassword(secretKey, "xxxxxxxx"), "dan", "pro", RoleName.guest);

        when(userDAO.getEntityByEmail("danichTest@gmail.com")).thenReturn(user);
        when(keyDAO.getEntityByUserId(anyInt())).thenReturn(new Key(0, new Base64().encodeToString(secretKey.getEncoded())));

        assertFalse(userService.authenticate("danichTest@gmail.com", "zzzzzzzz"));
    }

    @Test
    public void testUpdateUser() {
        when(userDAO.updateEntity(any(User.class))).thenReturn(true);
        assertTrue(userDAO.updateEntity(any(User.class)));
    }
}
