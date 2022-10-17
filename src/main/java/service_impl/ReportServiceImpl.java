package service_impl;

import dao.CheckDAO;
import dao.ReportDAO;
import dao.UserDAO;
import dao_impl.UserDAOImpl;
import dto.ReportDTO;
import entity.CheckEntity;
import entity.ReportEntity;
import dao_impl.CheckDAOImpl;
import dao_impl.ReportDAOImpl;
import entity.User;
import service.ReportService;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static util.db.DBFields.*;

public class ReportServiceImpl implements ReportService {
    private static ReportServiceImpl instance = null;
    private final ReportDAO reportDAO = ReportDAOImpl.getInstance();
    private final CheckDAO checkDAO = CheckDAOImpl.getInstance();
    private final UserDAO userDAO = UserDAOImpl.getInstance();

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
            total_price += checkEntity.getQuantity() * checkEntity.getProduct().getPrice();
        }

        return reportDAO.insertEntity(new ReportEntity(0, username, Timestamp.from(Instant.now()), check.size(), total_price));
    }

    @Override
    public List<ReportEntity> getPerPage(int nOfPage, int total, String sortBy, boolean ifAscending) {
        String sortColumn = REPORT_ID;
        if (Objects.equals(sortBy, "createdBy"))
            sortColumn = REPORT_CREATED_BY;
        else if (Objects.equals(sortBy, "quantity"))
            sortColumn = REPORT_ITEMS_QUANTITY;
        else if (Objects.equals(sortBy, "price"))
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
    public List<ReportDTO> convertToDTO(List<ReportEntity> report) {
        List<ReportDTO> result = new ArrayList<>();

        report.forEach(el -> result.add(new ReportDTO(report.indexOf(el) + 1, el.getCreatedBy(),
                el.getClosed_at().toLocalDateTime().toString().replace("T", " "),
                el.getItems_quantity(), String.format("%.2f", el.getTotal_price()))));
        return result;
    }
}
