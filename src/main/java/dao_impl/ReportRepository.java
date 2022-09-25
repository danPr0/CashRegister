package dao_impl;

import dao.ReportDAO;
import entity.ReportElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static util.DBFields.*;

public class ReportRepository implements ReportDAO {
    private static ReportRepository instance = null;

    private static final String INSERT_REPORT_QUERY = "INSERT INTO report (%s, %s, %s, %s) VALUES (?, ?, ?, ?)"
            .formatted(REPORT_CREATED_BY, REPORT_CLOSED_AT, REPORT_ITEMS_QUANTITY, REPORT_TOTAL_PRICE);
    private static final String GET_NUMBER_OF_ROWS = "SELECT COUNT(*) FROM report";
    private static final String GET_LIMIT_QUERY = "SELECT * FROM report LIMIT ? OFFSET ?";
    private static final String GET_ALL_QUERY = "SELECT * FROM report";
    private static final String DELETE_ALL_QUERY = "DELETE FROM report";

    private final Logger logger = LogManager.getLogger(ProductRepository.class);

    private ReportRepository() {
        super();
    }

    public static ReportRepository getInstance() {
        if (instance == null)
            instance = new ReportRepository();
        return instance;
    }

    private Connection getConnection() {
        return ConnectionFactory.getInstance().getConnection();
    }

    public boolean insertReportElement(ReportElement reportElement) {
        boolean result = true;

        try (PreparedStatement ps = getConnection().prepareStatement(INSERT_REPORT_QUERY)) {
            ps.setString(1, reportElement.getUsername());
            ps.setDate(2, reportElement.getClosed_at());
            ps.setInt(3, reportElement.getItems_quantity());
            ps.setDouble(4, reportElement.getTotal_price());
            ps.execute();
            logger.info("Report element created by " + reportElement.getUsername() + " was successfully added");
        } catch (SQLException e) {
            e.printStackTrace();
            result = false;
        }

        return result;
    }

    @Override
    public int getNumberOfRows() {
        int result = 0;

        try (PreparedStatement ps = getConnection().prepareStatement(GET_NUMBER_OF_ROWS)) {
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    result = resultSet.getInt("count(*)");
                    logger.info("Number of rows in report were successfully retrieved");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public List<ReportElement> getLimit(int offset, int limit) {
        List<ReportElement> resultList = new ArrayList<>();

        try (PreparedStatement ps = getConnection().prepareStatement(GET_LIMIT_QUERY)) {
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    resultList.add(new ReportElement(resultSet.getInt(REPORT_ID),
                            resultSet.getString(REPORT_CREATED_BY), resultSet.getDate(REPORT_CLOSED_AT),
                            resultSet.getInt(REPORT_ITEMS_QUANTITY), resultSet.getDouble(REPORT_TOTAL_PRICE)));
                }
                logger.info(resultList.size() + " report elements were successfully retrieved");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultList;
    }

    @Override
    public List<ReportElement> getAll() {
        List<ReportElement> resultList = new ArrayList<>();

        try (PreparedStatement ps = getConnection().prepareStatement(GET_ALL_QUERY)) {
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    resultList.add(new ReportElement(resultSet.getInt(REPORT_ID),
                            resultSet.getString(REPORT_CREATED_BY), resultSet.getDate(REPORT_CLOSED_AT),
                            resultSet.getInt(REPORT_ITEMS_QUANTITY), resultSet.getDouble(REPORT_TOTAL_PRICE)));
                }
                logger.info(resultList.size() + " report elements were successfully retrieved");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultList;
    }

    @Override
    public boolean deleteAll() {
        boolean result = true;

        try (PreparedStatement ps = getConnection().prepareStatement(DELETE_ALL_QUERY)) {
            ps.execute(DELETE_ALL_QUERY);
            logger.info("All report elements were successfully deleted");
        } catch (SQLException e) {
            e.printStackTrace();
            result = false;
        }

        return result;
    }
}
