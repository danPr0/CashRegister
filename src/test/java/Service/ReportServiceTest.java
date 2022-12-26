package Service;

import dao.*;
import dao_impl.*;
import entity.CheckEntity;
import entity.Product;
import entity.ReportEntity;
import entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import service_impl.ProductServiceImpl;
import service_impl.ReportServiceImpl;
import util.enums.ProductMeasure;
import util.enums.RoleName;
import util.table.CheckColumnName;
import util.table.ReportColumnName;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static util.db.DBFields.*;

public class ReportServiceTest extends Mockito {
    private ReportServiceImpl reportService;
    private ReportDAO reportDAO;
    private ProductDAO productDAO;
    private CheckDAO checkDAO;
    private UserDAO userDAO;

    private User user;
    private Product product;
    private CheckEntity checkEntity;

    @BeforeEach
    public void init() {
        reportService = ReportServiceImpl.getInstance();
        reportDAO = mock(ReportDAOImpl.class);
        productDAO = mock(ProductDAOImpl.class);
        checkDAO = mock(CheckDAOImpl.class);
        userDAO = mock(UserDAOImpl.class);

        reportService.setProductDAO(productDAO);
        reportService.setCheckDAO(checkDAO);
        reportService.setReportDAO(reportDAO);
        reportService.setUserDAO(userDAO);

        user = new User(0, "test@ukr.net", "", "", "", RoleName.admin, true);
        product = new Product(0, new HashMap<>(), ProductMeasure.weight, 2, 5);
        checkEntity = new CheckEntity(0, product.getId(), 5);
//        try (MockedStatic<ProductDAOImpl> productDAOMocked = mockStatic(ProductDAOImpl.class)) {
//            productDAOMocked.when(ProductDAOImpl::getInstance).thenReturn(reportDAO);
//        }
    }

    @Test
    public void createReport() {
        String email = "test@ukr.net";
//        Product
        List<CheckEntity> check = List.of(checkEntity);
//        check.add(new CheckEntity(0, 0, 5));

        when(userDAO.getEntityByEmail(user.getEmail())).thenReturn(user);
        when(checkDAO.getAll()).thenReturn(check);
        when(productDAO.getEntityById(product.getId())).thenReturn(product);

        when(reportDAO.insertEntity(any(ReportEntity.class))).then(invocationOnMock -> {
            ReportEntity param = invocationOnMock.getArgument(0);
            assertEquals(user.getId(), param.getUserId());
            assertEquals(1, param.getItemsQuantity());
            assertEquals(checkEntity.getQuantity() * product.getPrice(), param.getTotalPrice());
            return true;
        });
//        when(reportDAO.insertEntity(any(ReportEntity.class))).thenReturn(true);
        assertTrue(reportService.createReport(user.getEmail()));
//        verify(reportDAO).insertEntity(new ReportEntity(anyInt(), anyInt(), any(Timestamp.class), anyInt(), anyDouble()));
    }

    @Test
    public void testGetPerPage() {
        when(reportDAO.getSegment(1, 1, REPORT_ITEMS_QUANTITY, "ASC")).thenReturn(List.of(new ReportEntity(), new ReportEntity()));
        assertEquals(2, reportService.getPerPage(2, 1, ReportColumnName.quantity, true).size());
    }

    @Test
    public void testGetNoOfRows() {
        when(reportDAO.getNoOfRows()).thenReturn(3);
        assertEquals(3, reportService.getNoOfRows());
    }

    @Test
    public void testGetAll() {
        when(reportDAO.getAll()).thenReturn(List.of(new ReportEntity()));
        assertEquals(1, reportService.getAll().size());
    }

    @Test
    public void testDeleteAll() {
        when(reportDAO.deleteAll()).thenReturn(true);
        assertTrue(reportService.deleteAll());
    }
}
