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
import util.enums.RoleName;

import javax.crypto.SecretKey;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest extends Mockito {
    private UserServiceImpl userService;
    private UserDAO userDAO;
    private KeyDAO keyDAO;

    private User user;

    @BeforeEach
    public void init() {
        userService = UserServiceImpl.getInstance();
        userDAO = mock(UserDAOImpl.class);
        keyDAO = mock(KeyDAOImpl.class);

        userService.setUserDAO(userDAO);
        userService.setKeyDAO(keyDAO);

        user = new User(0, "danichTest@gmail.com", "xxxxxxxx", "dan", "pro", RoleName.guest, true);

//        try (MockedStatic<UserDAOImpl> userDAOMocked = mockStatic(UserDAOImpl.class)) {
//            userDAOMocked.when(UserDAOImpl::getInstance).thenReturn(userDAO);
//        }
    }

    @Test
    public void testUserInsertion() {
        when(userDAO.insertEntity(any(User.class))).then(invocationOnMock -> {
            assertEquals(user.getEmail(), ((User) invocationOnMock.getArgument(0)).getEmail());
            return user;
        });
        when(keyDAO.insertEntity(any(Key.class))).thenReturn(true);

        assertTrue(userService.addUser("danichTest@gmail.com", "xxxxxxxx", "dan", "pro", RoleName.guest, true));
    }

    @Test
    public void testFindUser() {
        when(userDAO.getEntityByEmail("danichTest@gmail.com")).thenReturn(user);
        User foundUser = userService.getUser("danichTest@gmail.com");

        assertNotNull(foundUser);
        assertEquals(user.getEmail(), foundUser.getEmail());
    }

    @Test
    public void testAuthentication() {
        SecretKey secretKey = UserServiceImpl.AESUtil.generateSecretKey();
        user.setPassword(userService.encryptPassword(secretKey, "xxxxxxxx"));

        when(userDAO.getEntityByEmail("danichTest@gmail.com")).thenReturn(user);
        when(keyDAO.getEntityByUserId(user.getId())).thenReturn(new Key(0, new Base64().encodeToString(secretKey.getEncoded())));

        assertTrue(userService.authenticate("danichTest@gmail.com", "xxxxxxxx"));
    }

    @Test
    public void testFailAuthentication() {
        SecretKey secretKey = UserServiceImpl.AESUtil.generateSecretKey();
        user.setPassword(userService.encryptPassword(secretKey, "xxxxxxxx"));

        when(userDAO.getEntityByEmail("danichTest@gmail.com")).thenReturn(user);
        when(keyDAO.getEntityByUserId(user.getId())).thenReturn(new Key(0, new Base64().encodeToString(secretKey.getEncoded())));

        assertFalse(userService.authenticate("danichTest@gmail.com", "zzzzzzzz"));
    }

    @Test
    public void testUpdateUser() {
        when(userDAO.updateEntity(user)).thenReturn(true);
        assertTrue(userService.updateUser(user));
    }
}
