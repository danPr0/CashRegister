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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReportServiceTest extends Mockito {
    private ReportServiceImpl reportService;
    private ReportDAO reportDAO;
    private ProductDAO productDAO;
    private CheckDAO checkDAO;
    private UserDAO userDAO;

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

//        try (MockedStatic<ProductDAOImpl> productDAOMocked = mockStatic(ProductDAOImpl.class)) {
//            productDAOMocked.when(ProductDAOImpl::getInstance).thenReturn(reportDAO);
//        }
    }

    @Test
    public void createReport() {
        String email = "test@ukr.net";
        Product product = new Product(0, new HashMap<>(), ProductMeasure.weight, 2, 5);
        List<CheckEntity> check = new ArrayList<>();
        check.add(new CheckEntity(0, 0, 5));

        when(userDAO.getEntityByEmail(email)).thenReturn(new User(0, "", "", "", "", RoleName.admin));
        when(checkDAO.getAll()).thenReturn(check);
        when(productDAO.getEntityById(0)).thenReturn(product);


        when(reportDAO.insertEntity(any(ReportEntity.class))).then(invocationOnMock -> {
            assertEquals(0, ((ReportEntity) invocationOnMock.getArgument(0)).getUserId());
            assertEquals(1, ((ReportEntity) invocationOnMock.getArgument(0)).getItemsQuantity());
            assertEquals(25, ((ReportEntity) invocationOnMock.getArgument(0)).getTotalPrice());
            return true;
        });
//        when(reportDAO.insertEntity(any(ReportEntity.class))).thenReturn(true);
        assertTrue(reportService.createReport(email));
//        verify(reportDAO).insertEntity(new ReportEntity(anyInt(), anyInt(), any(Timestamp.class), anyInt(), anyDouble()));
    }

    @Test
    public void testGetNoOfRows() {
        when(reportDAO.getNoOfRows()).thenReturn(3);
        assertEquals(3, reportService.getNoOfRows());
    }

    @Test
    public void testGetAll() {
        when(reportDAO.getAll()).thenReturn(new ArrayList<>());
        assertNotNull(reportService.getAll());
    }

    @Test
    public void testDeleteAll() {
        when(reportDAO.deleteAll()).thenReturn(true);
        assertTrue(reportService.deleteAll());
    }
}
