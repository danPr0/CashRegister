package DAO;

import dao.ProductDAO;
import dao.ProductTranslationDAO;
import dao_impl.ProductDAOImpl;
import dao_impl.ProductTranslationDAOImpl;
import entity.Product;
import exception.ProductTranslationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.db.ConnectionFactory;
import util.enums.Language;
import util.enums.ProductMeasure;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static util.db.DBFields.*;

public class ProductDAOTest {
    private final ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
    private final ProductDAO productDAO = ProductDAOImpl.getInstance();
    private final ProductTranslationDAO productTranslationDAO = ProductTranslationDAOImpl.getInstance();

    private int PRODUCT_1_ID = 0;
    private final String PRODUCT_1_NAME = "sugar";
    private final String PRODUCT_1_TRANSLATION = "цукор";

    private final int PRODUCT_2_ID = 0;
    private final String PRODUCT_2_NAME = "water";
    private final String PRODUCT_2_TRANSLATION = "вода";

    private final Language ORIGINAL_LANG = Language.en;
    private final Language TRANSLATION_LANG = Language.ua;

    @BeforeEach
    public void insertRows() {
        try (Connection con = connectionFactory.getConnection();
             PreparedStatement psInsertProduct = con.prepareStatement("INSERT INTO products (%s, %s, %s, %s, %s) VALUES (?, ?, ?, '1', '1')"
                     .formatted(PRODUCT_ORIGINAL_NAME, PRODUCT_ORIGINAL_LANG_ID, PRODUCT_MEASURE, PRODUCT_QUANTITY, PRODUCT_PRICE), Statement.RETURN_GENERATED_KEYS);
             PreparedStatement psInsertTranslations = con.prepareStatement("INSERT INTO products_translations (%s, %s, %s) VALUES (?, ?, ?)"
                     .formatted(PRODUCT_TRANSLATION_PRODUCT_ID, PRODUCT_TRANSLATION_LANG_ID, PRODUCT_TRANSLATION_PRODUCT_TRANSLATION))) {
            psInsertProduct.setString(1, PRODUCT_1_NAME);
            psInsertProduct.setString(2, ORIGINAL_LANG.name());
            psInsertProduct.setString(3, ProductMeasure.weight.name());
            psInsertProduct.execute();

            ResultSet rsInsertProduct = psInsertProduct.getGeneratedKeys();
            rsInsertProduct.next();
            PRODUCT_1_ID = rsInsertProduct.getInt(1);

            psInsertTranslations.setInt(1, PRODUCT_1_ID);
            psInsertTranslations.setString(2, ORIGINAL_LANG.name());
            psInsertTranslations.setString(3, PRODUCT_1_NAME);
            psInsertTranslations.execute();

            psInsertTranslations.setInt(1, PRODUCT_1_ID);
            psInsertTranslations.setString(2, TRANSLATION_LANG.name());
            psInsertTranslations.setString(3, PRODUCT_1_TRANSLATION);
            psInsertTranslations.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void deleteAllRows() {
        try (Connection con = connectionFactory.getConnection();
             PreparedStatement psDeleteTranslations = con.prepareStatement("DELETE FROM products_translations");
             PreparedStatement psDeleteProducts = con.prepareStatement("DELETE FROM products")) {
            psDeleteTranslations.execute();
            psDeleteProducts.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestFindByIdExistingProduct() {
        Product product = productDAO.getEntityById(PRODUCT_1_ID);
        assertNotNull(product);

        assertEquals(PRODUCT_1_ID, product.getId());
        assertEquals(PRODUCT_1_NAME, product.getProductTranslations().get(ORIGINAL_LANG));
        assertEquals(PRODUCT_1_TRANSLATION, product.getProductTranslations().get(TRANSLATION_LANG));
    }

    @Test
    public void TestFindByIdNonExistingProduct() {
        assertNull(productDAO.getEntityById(PRODUCT_2_ID));
    }

    @Test
    public void TestInsertNonExistingProduct() {
        Map<Language, String> productTranslations = new HashMap<>();
        productTranslations.put(ORIGINAL_LANG, PRODUCT_2_NAME);
        productTranslations.put(TRANSLATION_LANG, PRODUCT_2_TRANSLATION);
        Product product = new Product(PRODUCT_2_ID, productTranslations, ProductMeasure.apiece, 1, 1);

        assertDoesNotThrow(() -> productDAO.insertEntity(product, ORIGINAL_LANG));
        int productId = productTranslationDAO.getEntityByProductName(PRODUCT_2_TRANSLATION, TRANSLATION_LANG).getProductId();
        Product productInDB = productDAO.getEntityById(productId);

        assertNotNull(productInDB);
        assertEquals(product.getProductTranslations().get(ORIGINAL_LANG), productInDB.getProductTranslations().get(ORIGINAL_LANG));
        assertEquals(product.getProductTranslations().get(TRANSLATION_LANG), productInDB.getProductTranslations().get(TRANSLATION_LANG));
    }

    @Test
    public void TestInsertExistingProduct() {
        Map<Language, String> productTranslations = new HashMap<>();
        productTranslations.put(ORIGINAL_LANG, PRODUCT_1_NAME);
        productTranslations.put(TRANSLATION_LANG, PRODUCT_1_TRANSLATION);
        Product product = new Product(PRODUCT_1_ID, productTranslations, ProductMeasure.weight, 1, 1);

        assertThrows(ProductTranslationException.class, () -> productDAO.insertEntity(product, ORIGINAL_LANG));

        product.setId(PRODUCT_2_ID);
        assertThrows(ProductTranslationException.class, () -> productDAO.insertEntity(product, ORIGINAL_LANG));

        productTranslations.replace(ORIGINAL_LANG, PRODUCT_2_NAME);
        assertThrows(ProductTranslationException.class, () -> productDAO.insertEntity(product, ORIGINAL_LANG));
    }

    @Test
    public void TestUpdateExistingProduct() {
        Product product = productDAO.getEntityById(PRODUCT_1_ID);
        product.setQuantity(0);
        product.setPrice(5);
        assertTrue(productDAO.updateEntity(product));

        Product updatedProduct = productDAO.getEntityById(PRODUCT_1_ID);
        assertEquals(product.getQuantity(), updatedProduct.getQuantity());
        assertEquals(product.getPrice(), updatedProduct.getPrice());
    }

    @Test
    public void TestUpdateNonExistingProduct() {
        Map<Language, String> productTranslations = new HashMap<>();
        productTranslations.put(ORIGINAL_LANG, PRODUCT_2_NAME);
        productTranslations.put(TRANSLATION_LANG, PRODUCT_2_TRANSLATION);
        Product product = new Product(PRODUCT_2_ID, productTranslations, ProductMeasure.apiece, 1, 1);

        assertFalse(productDAO.updateEntity(product));
    }
}
