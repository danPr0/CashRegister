package dao_impl;

import dao.ProductTranslationDAO;
import entity.ProductTranslation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.db.ConnectionFactory;
import util.enums.Language;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static util.db.DBFields.*;
import static util.db.DBQueryConstants.*;

public class ProductTranslationDAOImpl implements ProductTranslationDAO {
    private static ProductTranslationDAOImpl instance = null;
    private final ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
    private final Logger logger = LogManager.getLogger(ProductTranslationDAOImpl.class);

    private ProductTranslationDAOImpl() {
    }

    public static ProductTranslationDAOImpl getInstance() {
        if (instance == null)
            instance = new ProductTranslationDAOImpl();
        return instance;
    }

//    @Override
//    public boolean insertEntity(ProductTranslation entity, Connection connection) {
//        boolean result = true;
//
//        try (PreparedStatement ps = connection.prepareStatement(PRODUCTS_TRANSLATIONS_INSERT)) {
//            ps.setInt(1, entity.getProductId());
//            ps.setString(2, entity.getLangId());
//            ps.setString(3, entity.getTranslation());
//            ps.execute();
//            logger.info("Product lang was added");
//        } catch (SQLException e) {
//            result = false;
//            logger.error("Cannot insert product lang", e.getCause());
//        }
//
//        return result;
//    }

    @Override
    public ProductTranslation getEntityByProductName(String productName, Language langId) {
        ProductTranslation result = null;

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(PRODUCTS_TRANSLATIONS_GET_BY_PRODUCT_TRANSLATION)) {
            ps.setString(1, langId.name());
            ps.setString(2, productName);
            try (ResultSet resultSet = ps.executeQuery()) {
                resultSet.next();
                result = new ProductTranslation(resultSet.getInt(PRODUCT_TRANSLATION_PRODUCT_ID),
                        langId, productName);
                logger.info("Product name entity with productName=" + productName + " was retrieved");
            }
        } catch (SQLException e) {
            logger.error("Cannot get product name entity with productName=" + productName, e.getCause());
        }

        return result;
    }

//    @Override
//    public String getProductName(int productId, Language lang) {
//        String result = null;
//
//        try (Connection connection = connectionFactory.getConnection();
//             PreparedStatement ps = connection.prepareStatement(PRODUCT_TRANSLATION_GET_PRODUCT_NAME)){
//            ps.setInt(1, productId);
//            ps.setString(2, lang.name());
//            try (ResultSet resultSet = ps.executeQuery()) {
//                if (resultSet.next()) {
//                    result = resultSet.getString(PRODUCT_TRANSLATION_PRODUCT_TRANSLATION);
//                    logger.info("Product name was retrieved");
//                }
//            }
//        }
//        catch (SQLException e) {
//            logger.error("Cannot get product name with productId=" + productId + " and langId=" + langId);
//        }
//
//        return result;
//    }


}
