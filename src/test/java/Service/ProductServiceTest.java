package Service;

import dao.ProductDAO;
import dao.ProductTranslationDAO;
import dao_impl.ProductDAOImpl;
import dao_impl.ProductTranslationDAOImpl;
import entity.Product;
import entity.ProductTranslation;
import exception.ProductTranslationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import service_impl.ProductServiceImpl;
import util.enums.Language;
import util.enums.ProductMeasure;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ProductServiceTest extends Mockito {
    private ProductServiceImpl productService;
    private ProductDAO productDAO;
    private ProductTranslationDAO productTranslationDAO;

    @BeforeEach
    public void init() {
        productService = ProductServiceImpl.getInstance();
        productDAO = mock(ProductDAOImpl.class);
        productTranslationDAO = mock(ProductTranslationDAOImpl.class);

        productService.setProductDAO(productDAO);
        productService.setProductTranslationDAO(productTranslationDAO);

//        try (MockedStatic<ProductDAOImpl> productDAOMocked = mockStatic(ProductDAOImpl.class)) {
//            productDAOMocked.when(ProductDAOImpl::getInstance).thenReturn(productDAO);
//        }
    }

    @Test
    public void testFindProductById() {
        Map<Language, String> productTranslations = new HashMap<>();
        productTranslations.put(Language.en, "sugar");
        productTranslations.put(Language.ua, "цукор");
        Product product = new Product(0, productTranslations, ProductMeasure.weight, 1, 1);
        ProductTranslation translation =new ProductTranslation(0, Language.en, productTranslations.get(Language.en));

        when(productTranslationDAO.getEntityByProductName(translation.getTranslation(), translation.getLangId())).thenReturn(translation);
        when(productDAO.getEntityById(0)).thenReturn(product);
        Product foundProduct = productService.getProduct(translation.getTranslation(), translation.getLangId());

        assertNotNull(foundProduct);
        assertEquals(product.getId(), foundProduct.getId());
        assertEquals(product.getProductTranslations().get(Language.ua), foundProduct.getProductTranslations().get(Language.ua));
    }

    @Test
    public void testFindProductByName() {
        Product product = new Product(0, new HashMap<>(), ProductMeasure.weight, 1, 1);

        when(productDAO.getEntityById(0)).thenReturn(product);
        Product foundProduct = productService.getProduct(0);
        verify(productDAO).getEntityById(anyInt());

        assertNotNull(foundProduct);
        assertEquals(product.getId(), foundProduct.getId());
    }

    @Test
    public void testAddNonExistingProduct() throws ProductTranslationException {
        Map<Language, String> productTranslations = new HashMap<>();
        productTranslations.put(Language.en, "sugar");
        productTranslations.put(Language.ua, "цукор");
        Product product = new Product(0, productTranslations, ProductMeasure.weight, 1, 1);

        doNothing().when(productDAO).insertEntity(product, Language.en);
        assertDoesNotThrow(() -> productService.addProduct(productTranslations, product.getMeasure(), product.getQuantity(), product.getPrice()));
    }

    @Test
    public void testAddExistingProduct() throws ProductTranslationException {
        Map<Language, String> productTranslations = new HashMap<>();
        productTranslations.put(Language.en, "sugar");
        productTranslations.put(Language.ua, "цукор");
        Product product = new Product(0, productTranslations, ProductMeasure.weight, 1, 1);

        doThrow(ProductTranslationException.class).when(productDAO).insertEntity(any(Product.class), eq(Language.en));
        assertThrows(ProductTranslationException.class, () -> productService.addProduct(productTranslations, product.getMeasure(), product.getQuantity(), product.getPrice()));
    }

    @Test
    public void testUpdateProduct() {
        Product product = new Product(0, new HashMap<>(), ProductMeasure.weight, 1, 1);

        when(productDAO.getEntityById(0)).thenReturn(product);
        product.setQuantity(2);
        product.setPrice(2);

        when(productDAO.updateEntity(product)).thenReturn(true);
        assertTrue(productService.updateProduct(0, 2, 2));
    }
}
