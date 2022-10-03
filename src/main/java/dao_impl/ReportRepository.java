package dao_impl;

import dao.ReportDAO;
import dto.ReportDTO;
import entity.ReportElement;
import jxl.write.DateTime;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static util.DBFields.*;

public class ReportRepository implements ReportDAO {
    private static ReportRepository instance = null;
    private final ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
    private final Logger logger = LogManager.getLogger(ReportRepository.class);

    private static final String INSERT_REPORT_QUERY = "INSERT INTO report (%s, %s, %s, %s) VALUES (?, ?, ?, ?)"
            .formatted(REPORT_CREATED_BY, REPORT_CLOSED_AT, REPORT_ITEMS_QUANTITY, REPORT_TOTAL_PRICE);
    private static final String GET_NUMBER_OF_ROWS = "SELECT COUNT(*) FROM report";
    private static final String GET_LIMIT_QUERY = "SELECT * FROM report ORDER BY %S LIMIT ? OFFSET ?";
    private static final String GET_ALL_QUERY = "SELECT * FROM report";
    private static final String DELETE_ALL_QUERY = "DELETE FROM report";

    private ReportRepository() {}

    public static ReportRepository getInstance() {
        if (instance == null)
            instance = new ReportRepository();
        return instance;
    }

    public boolean insertReportElement(ReportElement reportElement) {
        boolean result = true;

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_REPORT_QUERY)) {
            ps.setString(1, reportElement.getCreatedBy());
            ps.setTimestamp(2, reportElement.getClosed_at());
            ps.setInt(3, reportElement.getItems_quantity());
            ps.setDouble(4, reportElement.getTotal_price());
            ps.execute();
            logger.info("Report element created by " + reportElement.getCreatedBy() + " was successfully added");
        } catch (SQLException e) {
            e.printStackTrace();
            result = false;
        }

        return result;
    }

    @Override
    public int getNumberOfRows() {
        int result = 0;

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_NUMBER_OF_ROWS);
             ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    result = resultSet.getInt("count(*)");
                    logger.info("Number of rows in report were successfully retrieved");
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public List<ReportDTO> getLimit(int offset, int limit, String sortColumn) {
        List<ReportDTO> resultList = new ArrayList<>();

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(String.format(GET_LIMIT_QUERY, sortColumn))) {
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    resultList.add(new ReportDTO(resultSet.getString(REPORT_CREATED_BY), resultSet.getString(REPORT_CLOSED_AT),
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

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_ALL_QUERY);
             ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    resultList.add(new ReportElement(resultSet.getInt(REPORT_ID),
                            resultSet.getString(REPORT_CREATED_BY), resultSet.getTimestamp(REPORT_CLOSED_AT),
                            resultSet.getInt(REPORT_ITEMS_QUANTITY), resultSet.getDouble(REPORT_TOTAL_PRICE)));
                }
                logger.info(resultList.size() + " report elements were successfully retrieved");
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
            ps.execute(DELETE_ALL_QUERY);
            logger.info("All report elements were successfully deleted");
        } catch (SQLException e) {
            e.printStackTrace();
            result = false;
        }

        return result;
    }
}
