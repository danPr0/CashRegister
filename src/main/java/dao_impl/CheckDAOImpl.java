package dao_impl;

import dao.CheckDAO;
import entity.CheckEntity;
import entity.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.db.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static util.db.DBFields.*;
import static util.db.DBQueryConstants.*;

public class CheckDAOImpl implements CheckDAO {
    private static CheckDAOImpl instance;
    private final ProductDAOImpl productDAOImpl = ProductDAOImpl.getInstance();
    private final ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
    private final Logger logger = LogManager.getLogger(CheckDAOImpl.class);

    private CheckDAOImpl() {}

    public static CheckDAOImpl getInstance() {
        if (instance == null)
            instance = new CheckDAOImpl();
        return instance;
    }

    public CheckEntity getEntityByProduct(Product product) {
        CheckEntity result = null;

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(CHECK_GET_BY_PRODUCT_QUERY)) {
            ps.setInt(1, product.getId());
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    result = new CheckEntity(resultSet.getInt(CHECK_ID), product, resultSet.getDouble(CHECK_PRODUCT_QUANTITY));
                    logger.info("Check element " + product.getName() + " was successfully retrieved");
                }
            }
        } catch (SQLException e) {
            logger.error("Cannot get check element with productName=" + product.getName());
        }

        return result;
    }

    @Override
    public boolean insertEntity(CheckEntity checkEntity) {
        boolean result = true;

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(CHECK_INSERT_QUERY)) {
            ps.setInt(1, checkEntity.getProduct().getId());
            ps.setDouble(2, checkEntity.getQuantity());
            ps.execute();
            logger.info("Check element " + checkEntity.getProduct().getName()+ " was successfully added");
        } catch (SQLException e) {
            logger.error("Cannot insert check element with productName=" + checkEntity.getProduct().getName());
            result = false;
        }

        return result;
    }

    @Override
    public boolean updateEntity(CheckEntity checkEntity) {
        boolean result = true;

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(CHECK_UPDATE_BY_ID_QUERY)) {
            ps.setDouble(1, checkEntity.getQuantity());
            ps.setInt(2, checkEntity.getId());
            ps.execute();
            logger.info("Check element " + checkEntity.getProduct().getName()+ " was successfully updated");
        } catch (SQLException e) {
            logger.error("Cannot update check element with productName=" + checkEntity.getProduct().getName());
            result = false;
        }

        return result;
    }

    @Override
    public boolean deleteEntityById(int id) {
        boolean result = true;

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(CHECK_DELETE_BY_ID_QUERY)) {
            ps.setInt(1, id);
            ps.execute();
            logger.info("Check element with id = " + id + " was successfully deleted");
        } catch (SQLException e) {
            logger.error("Cannot delete check element by id=" + id);
            result = false;
        }

        return result;
    }

    @Override
    public List<CheckEntity> getSegment(int offset, int limit, String sortColumn, String order) {
        List<CheckEntity> resultList = new ArrayList<>();

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(String.format(CHECK_GET_SEGMENT_QUERY, sortColumn, order))) {
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    resultList.add(new CheckEntity(resultSet.getInt(CHECK_ID),
                            productDAOImpl.getEntityById(resultSet.getInt(CHECK_PRODUCT_ID)),
                            resultSet.getDouble(CHECK_PRODUCT_QUANTITY)));
                }
                logger.info(resultList.size() + " check elements were successfully retrieved");
            }
        } catch (SQLException e) {
            logger.error("Cannot get segment of check from " + offset + " to " + limit);
        }

        return resultList;
    }

    @Override
    public int getNoOfRows() {
        int result = 0;

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(CHECK_GET_NUMBER_OF_ROWS);
             ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    result = resultSet.getInt("count(*)");
                    logger.info("Number of rows in check were successfully retrieved");
                }
        } catch (SQLException e) {
            logger.error("Cannot get number of rows in check");
        }

        return result;
    }

    @Override
    public List<CheckEntity> getAll() {
        List<CheckEntity> resultList = new ArrayList<>();

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(CHECK_GET_ALL_QUERY);
             ResultSet resultSet = ps.executeQuery()) {
            while (resultSet.next()) {
                resultList.add(new CheckEntity(resultSet.getInt(CHECK_ID),
                        productDAOImpl.getEntityById(resultSet.getInt(CHECK_PRODUCT_ID)),
                        resultSet.getDouble(CHECK_PRODUCT_QUANTITY)));
            }
            logger.info(resultList.size() + " check elements were successfully retrieved");
        } catch (SQLException e) {
            logger.error("Cannot get check");
        }

        return resultList;
    }

    @Override
    public boolean deleteAll() {
        boolean result = true;

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(CHECK_DELETE_ALL_QUERY)) {
            ps.execute();
            logger.info("All check elements were successfully deleted");
        } catch (SQLException e) {
            logger.error("Cannot delete check");
            result = false;
        }

        return result;
    }
}
