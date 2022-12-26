package DAO;

import dao.KeyDAO;
import dao.ReportDAO;
import dao.UserDAO;
import dao_impl.KeyDAOImpl;
import dao_impl.ReportDAOImpl;
import dao_impl.UserDAOImpl;
import entity.CheckEntity;
import entity.ReportEntity;
import entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.db.ConnectionFactory;
import util.db.DBUtil;
import util.enums.RoleName;

import java.sql.*;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static util.db.DBFields.*;
import static util.db.DBFields.REPORT_USER_ID;

public class ReportDAOTest {
    private final ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
    private final UserDAO userDAO = UserDAOImpl.getInstance();
    private final ReportDAO reportDAO = ReportDAOImpl.getInstance();

    private int REPORT_1_USER_ID = 0;

    private int REPORT_2_USER_ID = 0;

    @BeforeEach
    public void insertRows() {
        PreparedStatement ps = null;
        try (Connection con = connectionFactory.getConnection()) {
            ps = con.prepareStatement("INSERT INTO users (%s, %s, %s, %s, %s, %s) VALUES (?, 'xxxxxxxx', 'dan', 'pro', ?, true)"
                    .formatted(USER_EMAIL, USER_PASSWORD, USER_FIRST_NAME, USER_SECOND_NAME, USER_ROLE_ID, USER_ENABLED), Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, "1@mail.com");
            ps.setString(2, RoleName.admin.name());
            ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                generatedKeys.next();
                REPORT_1_USER_ID = generatedKeys.getInt(1);
            }

            ps = con.prepareStatement("INSERT INTO report (%s, %s, %s, %s) VALUES (?, ?, '1', '1')"
                    .formatted(REPORT_USER_ID, REPORT_CLOSED_AT, REPORT_ITEMS_QUANTITY, REPORT_TOTAL_PRICE));
            ps.setInt(1, REPORT_1_USER_ID);
            ps.setTimestamp(2, Timestamp.from(Instant.now()));
            ps.executeUpdate();

            ps = con.prepareStatement("INSERT INTO users (%s, %s, %s, %s, %s, %s) VALUES (?, 'xxxxxxxx', 'dan', 'pro', ?, true)"
                    .formatted(USER_EMAIL, USER_PASSWORD, USER_FIRST_NAME, USER_SECOND_NAME, USER_ROLE_ID, USER_ENABLED), Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, "2@mail.com");
            ps.setString(2, RoleName.admin.name());
            ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                generatedKeys.next();
                REPORT_2_USER_ID = generatedKeys.getInt(1);
            }

            ps = con.prepareStatement("INSERT INTO report (%s, %s, %s, %s) VALUES (?, ?, '1', '1')"
                    .formatted(REPORT_USER_ID, REPORT_CLOSED_AT, REPORT_ITEMS_QUANTITY, REPORT_TOTAL_PRICE));
            ps.setInt(1, REPORT_2_USER_ID);
            ps.setTimestamp(2, Timestamp.from(Instant.now()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DBUtil.close(null, ps);
        }
    }

    @AfterEach
    public void deleteAllRows() {
        PreparedStatement ps = null;
        try (Connection con = connectionFactory.getConnection()) {
            ps = con.prepareStatement("DELETE FROM report");
            ps.execute();

            ps = con.prepareStatement("DELETE FROM users");
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DBUtil.close(null, ps);
        }
    }

    @Test
    public void TestInsertEntity() {
        User user = userDAO.insertEntity(new User(0, "3gmail.com", "xxxxxxxx", "dan", "pro", RoleName.guest, true));
        ReportEntity reportEntity = new ReportEntity(0, user.getId(), Timestamp.from(Instant.now()), 1, 1);
        assertTrue(reportDAO.insertEntity(reportEntity));
    }

    @Test
    public void TestInsertEntityByNonExistingUser() {
        ReportEntity reportEntity = new ReportEntity(0, 0, Timestamp.from(Instant.now()), 1, 1);
        assertFalse(reportDAO.insertEntity(reportEntity));
    }

    @Test
    public void testGetSegment() {
        List<ReportEntity> segment = reportDAO.getSegment(1, 1, REPORT_USER_ID, "ASC");
        assertEquals(1, segment.size());
        assertEquals(REPORT_2_USER_ID, segment.get(0).getUserId());

        segment = reportDAO.getSegment(1, 1, REPORT_USER_ID, "DESC");
        assertEquals(1, segment.size());
        assertEquals(REPORT_1_USER_ID, segment.get(0).getUserId());

        segment = reportDAO.getSegment(0, 1, REPORT_USER_ID, "ASC");
        assertEquals(1, segment.size());
        assertEquals(REPORT_1_USER_ID, segment.get(0).getUserId());

        segment = reportDAO.getSegment(0, 2, REPORT_USER_ID, "DESC");
        assertEquals(2, segment.size());
    }

    @Test
    public void TestNumOfRows() {
        assertEquals(2, reportDAO.getNoOfRows());
    }

    @Test
    public void TestGetAll() {
        assertEquals(2, reportDAO.getAll().size());
    }

    @Test
    public void TestDeleteAll() {
        assertTrue(reportDAO.deleteAll());
        assertEquals(0, reportDAO.getNoOfRows());
    }
}
