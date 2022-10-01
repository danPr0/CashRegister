package dao_impl;

import dao.CheckDAO;
import entity.CheckElement;
import entity.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static util.DBFields.*;

public class CheckRepository implements CheckDAO {
    private static CheckRepository instance;
    private final ProductRepository productRepository = ProductRepository.getInstance();
    private final ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
    private final Logger logger = LogManager.getLogger(CheckRepository.class);

    private static final String GET_CHECK_BY_PRODUCT_QUERY = "SELECT * FROM `check` WHERE %s = ?".formatted(CHECK_PRODUCT_ID);
    private static final String GET_CHECK_BY_ID_QUERY = "SELECT * FROM `check` WHERE %s = ?".formatted(CHECK_ID);
    private static final String GET_NUMBER_OF_ROWS = "SELECT COUNT(*) FROM `check`";
    private static final String GET_LIMIT_QUERY = "SELECT * FROM `check` ORDER BY %s LIMIT ? OFFSET ?";
    private static final String GET_ALL_QUERY = "SELECT * FROM `check`";
    private static final String INSERT_CHECK_QUERY = "INSERT INTO `check` (%s, %s) VALUES (?, ?)"
            .formatted(CHECK_PRODUCT_ID, CHECK_PRODUCT_QUANTITY);
    private static final String UPDATE_CHECK_BY_ID_QUERY = "UPDATE `check` SET %s = ? WHERE %s = ?"
            .formatted(CHECK_PRODUCT_QUANTITY, CHECK_ID);
    private static final String DELETE_CHECK_BY_ID_QUERY = "DELETE FROM `check` WHERE %s = ?".formatted(CHECK_ID);
    private static final String DELETE_ALL_QUERY = "DELETE FROM `check`";

    private CheckRepository() {}

    public static CheckRepository getInstance() {
        if (instance == null)
            instance = new CheckRepository();
        return instance;
    }

    public CheckElement getCheckElementByProduct(Product product) {
        CheckElement result = null;

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_CHECK_BY_PRODUCT_QUERY)) {
            ps.setInt(1, product.getId());
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    result = new CheckElement(resultSet.getInt(CHECK_ID), product, resultSet.getInt(CHECK_PRODUCT_QUANTITY));
                    logger.info("Check element " + product.getName() + " was successfully retrieved");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public CheckElement getCheckElementById(int id) {
        CheckElement result = null;

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_CHECK_BY_ID_QUERY)) {
            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    result = new CheckElement(resultSet.getInt(CHECK_ID),
                            productRepository.getProductById(resultSet.getInt(CHECK_PRODUCT_ID)),
                            resultSet.getInt(CHECK_PRODUCT_QUANTITY));
                    logger.info("Check element with id = " + id+ " was successfully retrieved");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public boolean insertCheckElement(CheckElement checkElement) {
        boolean result = true;

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_CHECK_QUERY)) {
            ps.setInt(1, checkElement.getProduct().getId());
            ps.setInt(2, checkElement.getQuantity());
            ps.execute();
            logger.info("Check element " + checkElement.getProduct().getName()+ " was successfully added");
        } catch (SQLException e) {
            e.printStackTrace();
            result = false;
        }

        return result;
    }

    @Override
    public boolean updateCheckElement(CheckElement checkElement) {
        boolean result = true;

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE_CHECK_BY_ID_QUERY)) {
            ps.setInt(1, checkElement.getQuantity());
            ps.setInt(2, checkElement.getId());
            ps.execute();
            logger.info("Check element " + checkElement.getProduct().getName()+ " was successfully updated");
        } catch (SQLException e) {
            e.printStackTrace();
            result = false;
        }

        return result;
    }

    @Override
    public boolean deleteCheckElementById(int id) {
        boolean result = true;

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE_CHECK_BY_ID_QUERY)) {
            ps.setInt(1, id);
            ps.execute();
            logger.info("Check element with id = " + id + " was successfully deleted");
        } catch (SQLException e) {
            e.printStackTrace();
            result = false;
        }

        return result;
    }

    @Override
    public List<CheckElement> getAll() {
        List<CheckElement> resultList = new ArrayList<>();

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_ALL_QUERY);
             ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    resultList.add(new CheckElement(resultSet.getInt(CHECK_ID),
                            productRepository.getProductById(resultSet.getInt(CHECK_PRODUCT_ID)),
                            resultSet.getInt(CHECK_PRODUCT_QUANTITY)));
                }
                logger.info(resultList.size() + " check elements were successfully retrieved");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultList;
    }

    @Override
    public boolean deleteAll() {
        boolean result = true;

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE_ALL_QUERY)) {
            ps.execute();
            logger.info("All check elements were successfully deleted");
        } catch (SQLException e) {
            e.printStackTrace();
            result = false;
        }

        return result;
    }

    @Override
    public List<CheckElement> getLimit(int offset, int limit, String sortColumn) {
        List<CheckElement> resultList = new ArrayList<>();

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(String.format(GET_LIMIT_QUERY, sortColumn))) {
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    resultList.add(new CheckElement(resultSet.getInt(CHECK_ID),
                            productRepository.getProductById(resultSet.getInt(CHECK_PRODUCT_ID)),
                            resultSet.getInt(CHECK_PRODUCT_QUANTITY)));
                }
                logger.info(resultList.size() + " check elements were successfully retrieved");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultList;
    }



    @Override
    public int getNumberOfRows() {
        int result = 0;

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_NUMBER_OF_ROWS);
             ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    result = resultSet.getInt("count(*)");
                    logger.info("Number of rows in check were successfully retrieved");
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
