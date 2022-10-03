package service;

import dao_impl.UserRepository;
import dto.ReportDTO;
import entity.CheckElement;
import entity.ReportElement;
import dao_impl.CheckRepository;
import dao_impl.ReportRepository;
import entity.User;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static util.DBFields.*;

public class ReportService {
    private static ReportService instance = null;
    private final ReportRepository reportRepository = ReportRepository.getInstance();
    private final CheckRepository checkRepository = CheckRepository.getInstance();
    private final UserRepository userRepository = UserRepository.getInstance();

    private ReportService() {}

    public static ReportService getInstance() {
        if (instance == null)
            instance = new ReportService();
        return instance;
    }

    public boolean add(String username) {
        List<CheckElement> check = checkRepository.getAll();
        User user = userRepository.getUserByUsername(username);
        if (check.isEmpty() || user == null)
            return false;

        int items_quantity = 0;
        double total_price = 0;

        for (CheckElement checkElement : check) {
            items_quantity += checkElement.getQuantity();
            total_price += checkElement.getQuantity() * checkElement.getProduct().getPrice();
        }

        return reportRepository.insertReportElement(new ReportElement(0, username, Timestamp.from(Instant.now()), items_quantity, total_price));
    }

    public List<ReportDTO> getPerPage(int nOfPage, int total, String sortParameter) {
        String sortColumn = REPORT_ID;
        if (Objects.equals(sortParameter, "createdBy"))
            sortColumn = REPORT_CREATED_BY;
        else if (Objects.equals(sortParameter, "quantity"))
            sortColumn = REPORT_ITEMS_QUANTITY;
        else if (Objects.equals(sortParameter, "price"))
            sortColumn = REPORT_TOTAL_PRICE;

        return reportRepository.getLimit(total * (nOfPage - 1), total, sortColumn);
    }

    public int getNumberOfRows() {
        return reportRepository.getNumberOfRows();
    }

    public List<ReportElement> getAll() {
        return reportRepository.getAll();
    }

    public boolean deleteAll() {
        return reportRepository.deleteAll();
    }
}
