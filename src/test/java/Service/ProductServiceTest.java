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

    private Product product;

    @BeforeEach
    public void init() {
        productService = ProductServiceImpl.getInstance();
        productDAO = mock(ProductDAOImpl.class);
        productTranslationDAO = mock(ProductTranslationDAOImpl.class);

        productService.setProductDAO(productDAO);
        productService.setProductTranslationDAO(productTranslationDAO);

        Map<Language, String> productTranslations = new HashMap<>();
        productTranslations.put(Language.en, "sugar");
        productTranslations.put(Language.ua, "цукор");
        product = new Product(0, productTranslations, ProductMeasure.weight, 1, 1);

//        try (MockedStatic<ProductDAOImpl> productDAOMocked = mockStatic(ProductDAOImpl.class)) {
//            productDAOMocked.when(ProductDAOImpl::getInstance).thenReturn(productDAO);
//        }
    }

    @Test
    public void testFindProductById() {
        ProductTranslation translation = new ProductTranslation(product.getId(), Language.en, product.getProductTranslations().get(Language.en));

        when(productTranslationDAO.getEntityByProductName(translation.getTranslation(), translation.getLangId())).thenReturn(translation);
        when(productDAO.getEntityById(product.getId())).thenReturn(product);
        Product foundProduct = productService.getProduct(translation.getTranslation(), translation.getLangId());

        assertNotNull(foundProduct);
        assertEquals(product.getId(), foundProduct.getId());
        assertEquals(product.getProductTranslations().get(Language.ua), foundProduct.getProductTranslations().get(Language.ua));
    }

    @Test
    public void testFindProductByName() {
        when(productDAO.getEntityById(product.getId())).thenReturn(product);
        Product foundProduct = productService.getProduct(product.getId());

        assertNotNull(foundProduct);
        assertEquals(product.getId(), foundProduct.getId());
    }

    @Test
    public void testAddNonExistingProduct() throws ProductTranslationException {
        doNothing().when(productDAO).insertEntity(product, Language.en);
        assertDoesNotThrow(() -> productService.addProduct(product.getProductTranslations(), product.getMeasure(), product.getQuantity(), product.getPrice()));
    }

    @Test
    public void testAddExistingProduct() throws ProductTranslationException {
        doThrow(ProductTranslationException.class).when(productDAO).insertEntity(any(Product.class), eq(Language.en));
        assertThrows(ProductTranslationException.class, () -> productService.addProduct(product.getProductTranslations(), product.getMeasure(), product.getQuantity(), product.getPrice()));
    }

    @Test
    public void testUpdateProduct() {
        when(productDAO.getEntityById(product.getId())).thenReturn(product);
        product.setQuantity(2);
        product.setPrice(2);

        when(productDAO.updateEntity(product)).thenReturn(true);
        assertTrue(productService.updateProduct(0, 2, 2));
    }
}
