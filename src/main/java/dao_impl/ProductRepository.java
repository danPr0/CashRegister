package dao_impl;

import dao.ProductDAO;
import entity.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.ConnectionFactory;

import java.sql.*;

import static util.DBFields.*;

public class ProductRepository implements ProductDAO {
    private static ProductRepository instance = null;

    private static final String GET_PRODUCT_BY_NAME_QUERY = "SELECT * FROM products WHERE %s = ?".formatted(PRODUCT_NAME);
    private static final String GET_PRODUCT_BY_ID_QUERY = "SELECT * FROM products WHERE %s = ?".formatted(PRODUCT_ID);
    private static final String INSERT_PRODUCT_QUERY = "INSERT INTO products (%s, %s, %s) VALUES (?, ?, ?)"
            .formatted(PRODUCT_NAME, PRODUCT_QUANTITY, PRODUCT_PRICE);
    private static final String UPDATE_PRODUCT_QUERY = "UPDATE products SET %s = ?, %s = ? WHERE %s = ?"
            .formatted(PRODUCT_QUANTITY, PRODUCT_PRICE, PRODUCT_NAME);
    private static final String DELETE_PRODUCT_QUERY = "DELETE FROM products WHERE %s = ?".formatted(PRODUCT_ID);

    private final Logger logger = LogManager.getLogger(ProductRepository.class);

    private ProductRepository() {
        super();
    }

    public static ProductRepository getInstance() {
        if (instance == null)
            instance = new ProductRepository();
        return instance;
    }

    private Connection getConnection() {
        return ConnectionFactory.getInstance().getConnection();
    }

    @Override
    public Product getProductByName(String name) {
        Product result = null;

        try (PreparedStatement ps = getConnection().prepareStatement(GET_PRODUCT_BY_NAME_QUERY)) {
            ps.setString(1, name);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    result = new Product(resultSet.getInt(PRODUCT_ID), resultSet.getString(PRODUCT_NAME), resultSet.getInt(PRODUCT_QUANTITY),
                            resultSet.getDouble(PRODUCT_PRICE));
                    logger.info("Product " + name + " was successfully retrieved");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public Product getProductById(int id) {
        Product result = null;

        try (PreparedStatement ps = getConnection().prepareStatement(GET_PRODUCT_BY_ID_QUERY)) {
            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    result = new Product(resultSet.getInt(PRODUCT_ID), resultSet.getString(PRODUCT_NAME), resultSet.getInt(PRODUCT_QUANTITY),
                            resultSet.getDouble(PRODUCT_PRICE));
                    logger.info("Product with id = " + id + " was successfully retrieved");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public boolean insertProduct(Product product) {
        boolean result = true;

        try (PreparedStatement ps = getConnection().prepareStatement(INSERT_PRODUCT_QUERY)) {
            ps.setString(1, product.getName());
            ps.setInt(2, product.getQuantity());
            ps.setDouble(3, product.getPrice());
            ps.execute();
            logger.info("Product " + product.getName() + " was successfully added");
        } catch (SQLException e) {
            e.printStackTrace();
            result = false;
        }

        return result;
    }

    @Override
    public boolean updateProduct(Product product) {
        boolean result = true;

        try (PreparedStatement ps = getConnection().prepareStatement(UPDATE_PRODUCT_QUERY)) {
            ps.setInt(1, product.getQuantity());
            ps.setDouble(2, product.getPrice());
            ps.setString(3, product.getName());
            ps.execute();
            logger.info("Product " + product.getName() + " was successfully updated");
        } catch (SQLException e) {
            e.printStackTrace();
            result = false;
        }

        return result;
    }

    @Override
    public boolean deleteProduct(int id) {
        boolean result = true;

        try (PreparedStatement ps = getConnection().prepareStatement(DELETE_PRODUCT_QUERY)) {
            ps.setInt(1, id);
            ps.execute();
            logger.info("Product with id = " + id + " was successfully added");
        } catch (SQLException e) {
            e.printStackTrace();
            result = false;
        }

        return result;
    }
}
