package DAO;

import dao.CheckDAO;
import dao.ProductDAO;
import dao.ProductTranslationDAO;
import dao_impl.CheckDAOImpl;
import dao_impl.ProductDAOImpl;
import dao_impl.ProductTranslationDAOImpl;
import entity.CheckEntity;
import entity.Product;
import exception.ProductTranslationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.db.ConnectionFactory;
import util.db.DBUtil;
import util.enums.Language;
import util.enums.ProductMeasure;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static util.db.DBFields.CHECK_PRODUCT_ID;
import static util.db.DBFields.CHECK_PRODUCT_QUANTITY;

public class CheckDAOTest {
    private final ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
    private final ProductDAO productDAO = ProductDAOImpl.getInstance();
    private final CheckDAO checkDAO = CheckDAOImpl.getInstance();
    private final ProductTranslationDAO productTranslationDAO = ProductTranslationDAOImpl.getInstance();

    private int PRODUCT_1_ID = 0;
    private int PRODUCT_2_ID = 0;
    private final int PRODUCT_3_ID = 0;

    @BeforeEach
    public void insertRows() {
        insertProduct("sugar", "цукор", ProductMeasure.weight, 2, 2);
        PRODUCT_1_ID = productTranslationDAO.getEntityByProductName("sugar", Language.en).getProductId();
        insertProduct("water", "вода", ProductMeasure.apiece, 3, 3);
        PRODUCT_2_ID = productTranslationDAO.getEntityByProductName("water", Language.en).getProductId();

        PreparedStatement ps = null;
        try (Connection connection = connectionFactory.getConnection()) {
            ps = connection.prepareStatement("INSERT INTO `check` (%s, %s) VALUES (?, ?)"
                    .formatted(CHECK_PRODUCT_ID, CHECK_PRODUCT_QUANTITY));
            ps.setInt(1, PRODUCT_1_ID);
            ps.setDouble(2, 2);
            ps.execute();

            ps = connection.prepareStatement("INSERT INTO `check` (%s, %s) VALUES (?, ?)"
                    .formatted(CHECK_PRODUCT_ID, CHECK_PRODUCT_QUANTITY));
            ps.setInt(1, PRODUCT_2_ID);
            ps.setDouble(2, 3);
            ps.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DBUtil.close(ps, null);
        }
    }

    private void insertProduct(String originalName, String translation, ProductMeasure measure, double quantity, double price) {
        Map<Language, String> productTranslations = new HashMap<>();
        productTranslations.put(Language.en, originalName);
        productTranslations.put(Language.ua, translation);
        Product product = new Product(0, productTranslations, measure, quantity, price);
        assertDoesNotThrow(() -> productDAO.insertEntity(product, Language.en));
    }

    @AfterEach
    public void deleteRows() {
        PreparedStatement ps = null;
        try (Connection con = connectionFactory.getConnection()) {
            ps = con.prepareStatement("DELETE FROM `check`");
            ps.execute();

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
    public void testGetNoOfRows() {
        assertEquals(2, checkDAO.getNoOfRows());
    }

    @Test
    public void testInsertNonExistingEntity() {
        assertDoesNotThrow(() -> insertProduct("milk", "молоко", ProductMeasure.apiece, 3, 3));
        int productId = productTranslationDAO.getEntityByProductName("milk", Language.en).getProductId();
        CheckEntity checkEntity = new CheckEntity(0, productId, 3);
        assertTrue(checkDAO.insertEntity(checkEntity));
        assertEquals(3, checkDAO.getNoOfRows());
    }

    @Test
    public void testInsertExistingEntity() {
        CheckEntity checkEntity = new CheckEntity(0, PRODUCT_1_ID, 3);
        assertFalse(checkDAO.insertEntity(checkEntity));
        assertEquals(2, checkDAO.getNoOfRows());
    }

    @Test
    public void testGetExistingEntity() {
        CheckEntity checkEntity = checkDAO.getEntityByProductId(PRODUCT_1_ID);
        assertNotNull(checkEntity);
        assertEquals(PRODUCT_1_ID, checkEntity.getProductId());
    }

    @Test
    public void testGetNonExistingEntity() {
        CheckEntity checkEntity = checkDAO.getEntityByProductId(PRODUCT_3_ID);
        assertNull(checkEntity);
    }

    @Test
    public void testUpdateExistingEntity() {
        CheckEntity checkEntity = checkDAO.getEntityByProductId(PRODUCT_1_ID);
        checkEntity.setQuantity(1);
        checkDAO.updateEntity(checkEntity);
        assertEquals(checkEntity.getQuantity(), checkDAO.getEntityByProductId(PRODUCT_1_ID).getQuantity());
    }

    @Test
    public void testUpdateNonExistingEntity() {
        CheckEntity checkEntity = new CheckEntity(0, PRODUCT_3_ID, 3);
        assertFalse(checkDAO.updateEntity(checkEntity));
    }

    @Test
    public void testDeleteEntityById() {
        assertTrue(checkDAO.deleteEntityByProductId(PRODUCT_1_ID));
        assertEquals(1, checkDAO.getNoOfRows());
    }

    @Test
    public void testGetAll() {
        assertEquals(2, checkDAO.getAll().size());
    }

    @Test
    public void testDeleteAll() {
        assertTrue(checkDAO.deleteAll());
        assertEquals(0, checkDAO.getNoOfRows());
    }
}
