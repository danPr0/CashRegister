package service_impl;

import dao.CheckDAO;
import dao.ProductDAO;
import dao.ReportDAO;
import dao.UserDAO;
import dao_impl.ProductDAOImpl;
import dao_impl.UserDAOImpl;
import dto.ReportDTO;
import entity.CheckEntity;
import entity.Product;
import entity.ReportEntity;
import dao_impl.CheckDAOImpl;
import dao_impl.ReportDAOImpl;
import entity.User;
import service.ReportService;
import util.enums.Language;
import util.price_adapter.PriceAdapter;
import util.price_adapter.PriceAdapterFactory;
import util.table.ReportColumnName;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static util.db.DBFields.*;

public class ReportServiceImpl implements ReportService {
    private static ReportServiceImpl instance = null;
    private ReportDAO reportDAO = ReportDAOImpl.getInstance();
    private CheckDAO checkDAO = CheckDAOImpl.getInstance();
    private ProductDAO productDAO = ProductDAOImpl.getInstance();
    private UserDAO userDAO = UserDAOImpl.getInstance();

    private ReportServiceImpl() {}

    public static ReportServiceImpl getInstance() {
        if (instance == null)
            instance = new ReportServiceImpl();
        return instance;
    }

    @Override
    public boolean createReport(String username) {
        List<CheckEntity> check = checkDAO.getAll();
        User user = userDAO.getEntityByEmail(username);
        if (check.isEmpty() || user == null)
            return false;

        double total_price = 0;

        for (CheckEntity checkEntity : check) {
            Product product = productDAO.getEntityById(checkEntity.getProductId());
            total_price += checkEntity.getQuantity() * product.getPrice();
        }

        return reportDAO.insertEntity(new ReportEntity(0, user.getId(), Timestamp.from(Instant.now()), check.size(), total_price));
    }

    @Override
    public List<ReportEntity> getPerPage(int nOfPage, int total, ReportColumnName sortBy, boolean ifAscending) {
        String sortColumn = REPORT_ID;
        if (sortBy.equals(ReportColumnName.createdBy))
            sortColumn = REPORT_USER_ID;
        else if (sortBy.equals(ReportColumnName.quantity))
            sortColumn = REPORT_ITEMS_QUANTITY;
        else if (sortBy.equals(ReportColumnName.price))
            sortColumn = REPORT_TOTAL_PRICE;

        return reportDAO.getSegment(total * (nOfPage - 1), total, sortColumn, ifAscending ? "ASC" : "DESC");
    }

    @Override
    public int getNoOfRows() {
        return reportDAO.getNoOfRows();
    }

    @Override
    public List<ReportEntity> getAll() {
        return reportDAO.getAll();
    }

    @Override
    public boolean deleteAll() {
        return reportDAO.deleteAll();
    }

    @Override
    public List<ReportDTO> convertToDTO(List<ReportEntity> report, Language lang) {
        List<ReportDTO> result = new ArrayList<>();
        PriceAdapter priceAdapter = new PriceAdapterFactory().getAdapter(lang);

        report.forEach(el ->  {
            User user = userDAO.getEntityById(el.getUserId());
            result.add(new ReportDTO(report.indexOf(el) + 1, user.getFirstName() + " " + user.getSecondName(),
                    el.getClosedAt().toLocalDateTime().toString().replace("T", " "),
                    el.getItemsQuantity(), lang.getLocalPrice(priceAdapter.convertPrice(el.getTotalPrice()))));
        });

        return result;
    }

    public void setProductDAO(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public void setCheckDAO(CheckDAO checkDAO) {
        this.checkDAO = checkDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void setReportDAO(ReportDAO reportDAO) {
        this.reportDAO = reportDAO;
    }
}
