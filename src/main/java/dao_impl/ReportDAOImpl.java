package dao_impl;

import dao.ReportDAO;
import entity.ReportEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.db.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static util.db.DBFields.*;
import static util.db.DBQueryConstants.*;

public class ReportDAOImpl implements ReportDAO {
    private static ReportDAOImpl instance = null;
    private final ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
    private final Logger logger = LogManager.getLogger(ReportDAOImpl.class);

    private ReportDAOImpl() {}

    public static ReportDAOImpl getInstance() {
        if (instance == null)
            instance = new ReportDAOImpl();
        return instance;
    }

    @Override
    public boolean insertEntity(ReportEntity reportEntity) {
        boolean result = true;

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(REPORT_INSERT_QUERY)) {
            ps.setString(1, reportEntity.getCreatedBy());
            ps.setTimestamp(2, reportEntity.getClosed_at());
            ps.setInt(3, reportEntity.getItems_quantity());
            ps.setDouble(4, reportEntity.getTotal_price());
            ps.execute();
            logger.info("Report element created by " + reportEntity.getCreatedBy() + " was successfully added");
        } catch (SQLException e) {
            logger.error("Cannot insert report element");
            result = false;
        }

        return result;
    }

    @Override
    public List<ReportEntity> getSegment(int offset, int limit, String sortColumn, String order) {
        List<ReportEntity> resultList = new ArrayList<>();

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(String.format(REPORT_GET_SEGMENT_QUERY, sortColumn, order))) {
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    resultList.add(new ReportEntity(resultSet.getInt(REPORT_ID), resultSet.getString(REPORT_CREATED_BY), resultSet.getTimestamp(REPORT_CLOSED_AT),
                            resultSet.getInt(REPORT_ITEMS_QUANTITY), resultSet.getDouble(REPORT_TOTAL_PRICE)));
                }
                logger.info(resultList.size() + " report elements were successfully retrieved");
            }
        } catch (SQLException e) {
            logger.error("Cannot get segment of report from " + offset + " to " + limit);
        }

        return resultList;
    }

    @Override
    public int getNoOfRows() {
        int result = 0;

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(REPORT_GET_NUMBER_OF_ROWS);
             ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    result = resultSet.getInt("count(*)");
                    logger.info("Number of rows in report were successfully retrieved");
                }
        } catch (SQLException e) {
            logger.error("Cannot get number of rows in report");
        }

        return result;
    }

    @Override
    public List<ReportEntity> getAll() {
        List<ReportEntity> resultList = new ArrayList<>();

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(REPORT_GET_ALL_QUERY);
             ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    resultList.add(new ReportEntity(resultSet.getInt(REPORT_ID),
                            resultSet.getString(REPORT_CREATED_BY), resultSet.getTimestamp(REPORT_CLOSED_AT),
                            resultSet.getInt(REPORT_ITEMS_QUANTITY), resultSet.getDouble(REPORT_TOTAL_PRICE)));
                }
                logger.info(resultList.size() + " report elements were successfully retrieved");
        } catch (SQLException e) {
            logger.error("Cannot get report");
        }

        return resultList;
    }

    @Override
    public boolean deleteAll() {
        boolean result = true;

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(REPORT_DELETE_ALL_QUERY)) {
            ps.execute();
            logger.info("All report elements were successfully deleted");
        } catch (SQLException e) {
            logger.error("Cannot delete report");
            result = false;
        }

        return result;
    }
}
