package dao_impl;

import dao.ProductDAO;
import entity.Product;
import exception.ProductTranslationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.db.ConnectionFactory;
import util.db.DBUtil;
import util.enums.Language;
import util.enums.ProductMeasure;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import static util.db.DBFields.*;
import static util.db.DBQueryConstants.*;

public class ProductDAOImpl implements ProductDAO {
    private static ProductDAOImpl instance = null;
    private final ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
    private final Logger logger = LogManager.getLogger(ProductDAOImpl.class);

    private ProductDAOImpl() {
    }

    public static ProductDAOImpl getInstance() {
        if (instance == null)
            instance = new ProductDAOImpl();
        return instance;
    }

    @Override
    public Product getEntityById(int id) {
        Product result;

        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try (Connection con = connectionFactory.getConnection()) {
            con.setAutoCommit(false);
            ps = con.prepareStatement(PRODUCTS_GET_BY_ID_QUERY);
            ps.setInt(1, id);
            result = new Product();
            resultSet = ps.executeQuery();
            resultSet.next();

            result.setId(id);
            result.setMeasure(ProductMeasure.valueOf(resultSet.getString(PRODUCT_MEASURE)));
            result.setQuantity(resultSet.getDouble(PRODUCT_QUANTITY));
            result.setPrice(resultSet.getDouble(PRODUCT_PRICE));

            ps = con.prepareStatement(PRODUCTS_TRANSLATIONS_GET_BY_PRODUCT_ID);
            ps.setInt(1, id);
            resultSet = ps.executeQuery();

            Map<Language, String> productNames = new HashMap<>();
            while (resultSet.next()) {
                productNames.put(Language.valueOf(resultSet.getString(PRODUCT_TRANSLATION_LANG_ID)), resultSet.getString(PRODUCT_TRANSLATION_PRODUCT_TRANSLATION));
            }
            result.setProductTranslations(productNames);

            con.commit();
            logger.info("Product with id = " + id + " was successfully retrieved");
        } catch (SQLException e) {
            result = null;
            logger.error("Cannot get product by id=" + id, e.getCause());
        }
        finally {
            DBUtil.close(ps, resultSet);
        }

        return result;
    }

//    @Override
//    public Product getEntityByName(String name) {
//        Product result = null;
//
//        try (Connection con = connectionFactory.getConnection();
//             PreparedStatement ps = con.prepareStatement(PRODUCT_GET_BY_NAME_QUERY)) {
//            ps.setString(1, name);
//            try (ResultSet resultSet = ps.executeQuery()) {
//                if (resultSet.next()) {
//                    result = new Product(resultSet.getInt(PRODUCT_ID), resultSet.getString(PRODUCT_ORIGINAL_NAME),
//                            ProductMeasure.valueOf(resultSet.getString(PRODUCT_MEASURE)), resultSet.getDouble(PRODUCT_QUANTITY),
//                            resultSet.getDouble(PRODUCT_PRICE));
//                    logger.info("Product " + name + " was successfully retrieved");
//                }
//            }
//        } catch (SQLException e) {
//            logger.error("Cannot get product by name=" + name);
//        }
//
//        return result;
//    }

    @Override
    public void insertEntity(Product product, Language originalLang) throws ProductTranslationException {
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try (Connection connection = connectionFactory.getConnection()) {
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(PRODUCTS_INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, product.getProductTranslations().get(originalLang));
            ps.setString(2, originalLang.name());
            ps.setString(3, product.getMeasure().name());
            ps.setDouble(4, product.getQuantity());
            ps.setDouble(5, product.getPrice());
            ps.execute();

            resultSet = ps.getGeneratedKeys();
            resultSet.next();
            int productId = resultSet.getInt(1);
            for (Map.Entry<Language, String> entry : product.getProductTranslations().entrySet()) {
                ps = connection.prepareStatement(PRODUCTS_TRANSLATIONS_GET_BY_PRODUCT_TRANSLATION);
                ps.setString(1, entry.getKey().name());
                ps.setString(2, entry.getValue());
                resultSet = ps.executeQuery();
                if (resultSet.next()) {
                    connection.rollback();
                    throw new ProductTranslationException("Product translation already exists", entry.getKey());
                }

                ps = connection.prepareStatement(PRODUCTS_TRANSLATIONS_INSERT);
                ps.setInt(1, productId);
                ps.setString(2, entry.getKey().name());
                ps.setString(3, entry.getValue());
                ps.execute();
            }
            connection.commit();
            logger.info("Product " + product.getProductTranslations().get(originalLang) + " was successfully added");
        } catch (SQLException e) {
            logger.error("Cannot insert product with name=" + product.getProductTranslations().get(originalLang), e.getCause());
            throw new ProductTranslationException("Product translation already exists", originalLang);
        }
        finally {
            DBUtil.close(ps, resultSet);
        }
    }

    @Override
    public boolean updateEntity(Product product) {
        boolean result = true;

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(PRODUCTS_UPDATE_QUERY)) {
            ps.setDouble(1, product.getQuantity());
            ps.setDouble(2, product.getPrice());
            ps.setInt(3, product.getId());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0)
                throw new SQLException("Updating product failed, no rows affected");

            logger.info("Product was successfully updated");
        } catch (SQLException e) {
            logger.error("Cannot update product", e.getCause());
            result = false;
        }

        return result;
    }
}
