package DAO;

import dao.ProductDAO;
import dao.ProductTranslationDAO;
import dao.UserDAO;
import dao_impl.ProductDAOImpl;
import dao_impl.ProductTranslationDAOImpl;
import dao_impl.UserDAOImpl;
import entity.Product;
import entity.User;
import exception.ProductTranslationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import util.db.ConnectionFactory;
import util.db.DBUtil;
import util.enums.Language;
import util.enums.ProductMeasure;
import util.enums.RoleName;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static util.db.DBFields.*;
import static util.db.DBFields.USER_ROLE_ID;
import static util.db.DBQueryConstants.PRODUCTS_TRANSLATIONS_GET_BY_PRODUCT_TRANSLATION;
import static util.db.DBQueryConstants.PRODUCTS_TRANSLATIONS_INSERT;

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
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try (Connection con = connectionFactory.getConnection()) {
            ps = con.prepareStatement("INSERT INTO products (%s, %s, %s, %s, %s) VALUES (?, ?, ?, '1', '1')"
                    .formatted(PRODUCT_ORIGINAL_NAME, PRODUCT_ORIGINAL_LANG_ID, PRODUCT_MEASURE, PRODUCT_QUANTITY, PRODUCT_PRICE), Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, PRODUCT_1_NAME);
            ps.setString(2, ORIGINAL_LANG.name());
            ps.setString(3, ProductMeasure.weight.name());
            ps.execute();
            resultSet = ps.getGeneratedKeys();
            resultSet.next();
            PRODUCT_1_ID = resultSet.getInt(1);

            ps = con.prepareStatement("INSERT INTO products_translations (%s, %s, %s) VALUES (?, ?, ?)"
                    .formatted(PRODUCT_TRANSLATION_PRODUCT_ID, PRODUCT_TRANSLATION_LANG_ID, PRODUCT_TRANSLATION_PRODUCT_TRANSLATION));
            ps.setInt(1, PRODUCT_1_ID);
            ps.setString(2, ORIGINAL_LANG.name());
            ps.setString(3, PRODUCT_1_NAME);
            ps.execute();

            ps = con.prepareStatement("INSERT INTO products_translations (%s, %s, %s) VALUES (?, ?, ?)"
                    .formatted(PRODUCT_TRANSLATION_PRODUCT_ID, PRODUCT_TRANSLATION_LANG_ID, PRODUCT_TRANSLATION_PRODUCT_TRANSLATION));
            ps.setInt(1, PRODUCT_1_ID);
            ps.setString(2, TRANSLATION_LANG.name());
            ps.setString(3, PRODUCT_1_TRANSLATION);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(ps, resultSet);
        }
    }

    @AfterEach
    public void deleteAllRows() {
        PreparedStatement ps = null;
        try (Connection con = connectionFactory.getConnection()) {
            ps = con.prepareStatement("DELETE FROM products_translations");
            ps.execute();

            ps = con.prepareStatement("DELETE FROM products");
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(ps, null);
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
