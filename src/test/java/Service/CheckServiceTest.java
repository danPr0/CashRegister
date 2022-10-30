package Service;

import dao.CheckDAO;
import dao.ProductDAO;
import dao.ProductTranslationDAO;
import dao_impl.CheckDAOImpl;
import dao_impl.ProductDAOImpl;
import dao_impl.ProductTranslationDAOImpl;
import entity.CheckEntity;
import entity.Product;
import entity.ProductTranslation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;
import service_impl.CheckServiceImpl;
import util.enums.Language;

import static org.junit.jupiter.api.Assertions.*;

public class CheckServiceTest extends Mockito {
    private CheckServiceImpl checkService;
    private CheckDAO checkDAO;
    private ProductDAO productDAO;
    private ProductTranslationDAO productTranslationDAO;

    @BeforeEach
    public void init() {
        checkService = CheckServiceImpl.getInstance();
        productDAO = mock(ProductDAOImpl.class);
        productTranslationDAO = mock(ProductTranslationDAOImpl.class);
        checkDAO = mock(CheckDAOImpl.class);

        checkService.setCheckDAO(checkDAO);
        checkService.setProductDAO(productDAO);
        checkService.setProductTranslationDAO(productTranslationDAO);
    }

    @Test
    public void testGetByProductId() {
        when(productDAO.getEntityById(3)).thenReturn(Product.builder().id(3).build());
        when(checkDAO.getEntityByProductId(3)).thenReturn(CheckEntity.builder().id(1).build());

        CheckEntity checkEntity = checkDAO.getEntityByProductId(3);
        assertNotNull(checkEntity);
        assertEquals(1, checkEntity.getId());
    }

    @Test
    public void testGetByProductName() {
        String productName = "sugar";
        int productId = 3;
        ProductTranslation productTranslation = ProductTranslation.builder().productId(productId).build();

        when(productTranslationDAO.getEntityByProductName(productName, Language.en)).thenReturn(productTranslation);
        when(productDAO.getEntityById(productId)).thenReturn(Product.builder().id(productId).build());
        when(checkDAO.getEntityByProductId(productId)).thenReturn(CheckEntity.builder().id(1).build());

        CheckEntity checkEntity = checkService.getCheckElement(productName, Language.en);
        assertNotNull(checkEntity);
        assertEquals(1, checkEntity.getId());
    }



    @Test
    public void testCancelCheckElement() {

    }

    @Test
    public void testCloseCheck() {
        when(checkDAO.deleteAll()).thenReturn(true);
        assertTrue(checkService.closeCheck());
    }

    @Test
    public void testGetNoOfRows() {
        when(checkDAO.getNoOfRows()).thenReturn(3);
        assertEquals(3, checkService.getNumberOfRows());
    }
}
