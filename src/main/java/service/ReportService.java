package service;

import dao_impl.UserRepository;
import entity.CheckElement;
import entity.ReportElement;
import dao_impl.CheckRepository;
import dao_impl.ReportRepository;
import entity.User;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

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

        return reportRepository.insertReportElement(new ReportElement(0, username, Date.valueOf(LocalDate.now()), items_quantity, total_price));
    }

    public List<ReportElement> getPerPage(int nOfPage, int total) {
        return reportRepository.getLimit(total * (nOfPage - 1), total);
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
