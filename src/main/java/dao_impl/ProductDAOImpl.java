package dao_impl;

import dao.ProductDAO;
import entity.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.db.ConnectionFactory;
import util.ProductMeasure;

import java.sql.*;

import static util.db.DBFields.*;
import static util.db.DBQueryConstants.*;

public class ProductDAOImpl implements ProductDAO {
    private static ProductDAOImpl instance = null;
    private final ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
    private final Logger logger = LogManager.getLogger(ProductDAOImpl.class);

    private ProductDAOImpl() {}

    public static ProductDAOImpl getInstance() {
        if (instance == null)
            instance = new ProductDAOImpl();
        return instance;
    }

    @Override
    public Product getEntityById(int id) {
        Product result = null;

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(PRODUCT_GET_BY_ID_QUERY)) {
            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    result = new Product(resultSet.getInt(PRODUCT_ID), resultSet.getString(PRODUCT_NAME),
                            ProductMeasure.valueOf(resultSet.getString(PRODUCT_MEASURE)), resultSet.getDouble(PRODUCT_QUANTITY),
                            resultSet.getDouble(PRODUCT_PRICE));
                    logger.info("Product with id = " + id + " was successfully retrieved");
                }
            }
        } catch (SQLException e) {
            logger.error("Cannot get product by id=" + id);
        }

        return result;
    }

    @Override
    public Product getEntityByName(String name) {
        Product result = null;

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(PRODUCT_GET_BY_NAME_QUERY)) {
            ps.setString(1, name);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    result = new Product(resultSet.getInt(PRODUCT_ID), resultSet.getString(PRODUCT_NAME),
                            ProductMeasure.valueOf(resultSet.getString(PRODUCT_MEASURE)), resultSet.getDouble(PRODUCT_QUANTITY),
                            resultSet.getDouble(PRODUCT_PRICE));
                    logger.info("Product " + name + " was successfully retrieved");
                }
            }
        } catch (SQLException e) {
            logger.error("Cannot get product by name=" + name);
        }

        return result;
    }

    @Override
    public boolean insertEntity(Product product) {
        boolean result = true;

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(PRODUCT_INSERT_QUERY)) {
            ps.setString(1, product.getName());
            ps.setString(2, product.getMeasure().toString());
            ps.setDouble(3, product.getQuantity());
            ps.setDouble(4, product.getPrice());
            ps.execute();
            logger.info("Product " + product.getName() + " was successfully added");
        } catch (SQLException e) {
            logger.error("Cannot insert product with name=" + product.getName());
            result = false;
        }

        return result;
    }

    @Override
    public boolean updateEntity(Product product) {
        boolean result = true;

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(PRODUCT_UPDATE_QUERY)) {
            ps.setDouble(1, product.getQuantity());
            ps.setDouble(2, product.getPrice());
            ps.setString(3, product.getName());
            ps.execute();
            logger.info("Product " + product.getName() + " was successfully updated");
        } catch (SQLException e) {
            logger.error("Cannot update product with name=" + product.getName());
            result = false;
        }

        return result;
    }
}
