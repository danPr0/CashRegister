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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import service_impl.CheckServiceImpl;
import util.enums.Language;
import util.enums.ProductMeasure;
import util.table.CheckColumnName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static util.db.DBFields.CHECK_PRODUCT_ID;

public class CheckServiceTest extends Mockito {
    private CheckServiceImpl checkService;
    private CheckDAO checkDAO;
    private ProductDAO productDAO;
    private ProductTranslationDAO productTranslationDAO;

    private Product product;
    private CheckEntity checkEntity;

    @BeforeEach
    public void init() {
        checkService = CheckServiceImpl.getInstance();
        productDAO = mock(ProductDAOImpl.class);
        productTranslationDAO = mock(ProductTranslationDAOImpl.class);
        checkDAO = mock(CheckDAOImpl.class);

        checkService.setCheckDAO(checkDAO);
        checkService.setProductDAO(productDAO);
        checkService.setProductTranslationDAO(productTranslationDAO);

        Map<Language, String> productTranslations = new HashMap<>();
        productTranslations.put(Language.en, "sugar");
        productTranslations.put(Language.ua, "цукор");
        product = new Product(1, productTranslations, ProductMeasure.weight, 5, 5);

        checkEntity = new CheckEntity(0, product.getId(), 4);
    }

    @Test
    public void testGetByProductId() {
        when(productDAO.getEntityById(product.getId())).thenReturn(product);
        when(checkDAO.getEntityByProductId(product.getId())).thenReturn(checkEntity);

        CheckEntity foundCheckEntity = checkService.getCheckElement(product.getId());
        assertNotNull(foundCheckEntity);
        assertEquals(checkEntity.getId(), foundCheckEntity.getId());
    }

    @Test
    public void testGetByProductName() {
        ProductTranslation productTranslation = new ProductTranslation(product.getId(), Language.en, product.getProductTranslations().get(Language.en));

        when(productTranslationDAO.getEntityByProductName(productTranslation.getTranslation(), productTranslation.getLangId())).thenReturn(productTranslation);
        when(productDAO.getEntityById(product.getId())).thenReturn(product);
        when(checkDAO.getEntityByProductId(product.getId())).thenReturn(checkEntity);

        CheckEntity foundCheckEntity = checkService.getCheckElement(productTranslation.getTranslation(), Language.en);
        assertNotNull(foundCheckEntity);
        assertEquals(checkEntity.getId(), foundCheckEntity.getId());
    }

    @Test
    public void testAddExistingCheckElement() {
        double quantityToAdd = 3;

        when(checkDAO.getEntityByProductId(product.getId())).thenReturn(checkEntity);
        when(productDAO.updateEntity(product)).then(invocationOnMock -> {
            assertEquals(2, ((Product) invocationOnMock.getArgument(0)).getQuantity());
            return true;
        });
        when(checkDAO.updateEntity(checkEntity)).then(invocationOnMock -> {
            assertEquals(7, ((CheckEntity) invocationOnMock.getArgument(0)).getQuantity());
            return true;
        });

        assertTrue(checkService.addToCheck(product, quantityToAdd));
        verify(checkDAO).updateEntity(checkEntity);
//
//        when(checkDAO.getEntityByProductId(product.getId())).thenReturn(null);
//        when(checkDAO.insertEntity(any(CheckEntity.class))).thenReturn(true);
//
//        product.setQuantity(product.getQuantity() + quantityToAdd);
//        assertTrue(checkService.addToCheck(product, quantityToAdd));
//        verify(checkDAO).insertEntity(any(CheckEntity.class));
    }

    @Test
    public void testAddNonExistingCheckElement() {
        double quantityToAdd = 3;

        when(checkDAO.getEntityByProductId(product.getId())).thenReturn(null);
        when(productDAO.updateEntity(product)).then(invocationOnMock -> {
            assertEquals(2, ((Product) invocationOnMock.getArgument(0)).getQuantity());
            return true;
        });
        when(checkDAO.insertEntity(any(CheckEntity.class))).thenReturn(true);

        assertTrue(checkService.addToCheck(product, quantityToAdd));
        verify(checkDAO).insertEntity(any(CheckEntity.class));
    }


    @Test
    public void testCancelCheckElement() {
        double quantityToCancel = 3;

        when(productDAO.getEntityById(product.getId())).thenReturn(product);
        when(productDAO.updateEntity(product)).then(invocationOnMock -> {
            assertEquals(8, ((Product) invocationOnMock.getArgument(0)).getQuantity());
            return true;
        });

        when(checkDAO.updateEntity(checkEntity)).then(invocationOnMock -> {
            assertEquals(1, ((CheckEntity) invocationOnMock.getArgument(0)).getQuantity());
            return true;
        });

        assertTrue(checkService.cancelCheckElement(checkEntity, quantityToCancel));
        verify(checkDAO).updateEntity(checkEntity);
//
//        when(checkDAO.deleteEntityByProductId(product.getId())).thenReturn(true);
//
//        assertTrue(checkService.cancelCheckElement(checkEntity, checkEntity.getQuantity()));
//        verify(checkDAO).deleteEntityByProductId(product.getId());
    }

    @Test
    public void testCancelWholeCheckElement() {
        when(productDAO.getEntityById(product.getId())).thenReturn(product);
        when(productDAO.updateEntity(product)).then(invocationOnMock -> {
            assertEquals(9, ((Product) invocationOnMock.getArgument(0)).getQuantity());
            return true;
        });
        when(checkDAO.deleteEntityByProductId(product.getId())).thenReturn(true);

        assertTrue(checkService.cancelCheckElement(checkEntity, checkEntity.getQuantity()));
        verify(checkDAO).deleteEntityByProductId(product.getId());
    }

    @Test
    public void testCancelCheck() {
        List<CheckEntity> check = List.of(checkEntity);

        when(checkDAO.getAll()).thenReturn(check);
        when(productDAO.getEntityById(product.getId())).thenReturn(product);
        when(productDAO.updateEntity(product)).then(invocationOnMock -> {
            assertEquals(9, ((Product) invocationOnMock.getArgument(0)).getQuantity());
            return true;
        });
        when(checkDAO.deleteAll()).thenReturn(true);

        assertTrue(checkService.cancelCheck());
        verify(productDAO).updateEntity(product);
    }

    @Test
    public void testGetPerPage() {
        when(checkDAO.getSegment(1, 1, CHECK_PRODUCT_ID, "ASC")).thenReturn(List.of(new CheckEntity(), new CheckEntity()));
        assertEquals(2, checkService.getPerPage(2, 1, CheckColumnName.productId, true).size());
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
