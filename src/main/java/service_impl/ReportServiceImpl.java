package service_impl;

import dao_impl.UserRepository;
import dto.ReportDTO;
import entity.CheckElement;
import entity.ReportElement;
import dao_impl.CheckRepository;
import dao_impl.ReportRepository;
import entity.User;
import service.ReportServiceInterface;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static util.DBFields.*;

public class ReportServiceImpl implements ReportServiceInterface {
    private static ReportServiceImpl instance = null;
    private final ReportRepository reportRepository = ReportRepository.getInstance();
    private final CheckRepository checkRepository = CheckRepository.getInstance();
    private final UserRepository userRepository = UserRepository.getInstance();

    private ReportServiceImpl() {}

    public static ReportServiceImpl getInstance() {
        if (instance == null)
            instance = new ReportServiceImpl();
        return instance;
    }

    @Override
    public boolean add(String username) {
        List<CheckElement> check = checkRepository.getAll();
        User user = userRepository.getUserByEmail(username);
        if (check.isEmpty() || user == null)
            return false;

        double total_price = 0;

        for (CheckElement checkElement : check) {
            total_price += checkElement.getQuantity() * checkElement.getProduct().getPrice();
        }

        return reportRepository.insertReportElement(new ReportElement(0, username, Timestamp.from(Instant.now()), check.size(), total_price));
    }

    @Override
    public List<ReportElement> getPerPage(int nOfPage, int total, String sortParameter) {
        String sortColumn = REPORT_ID;
        if (Objects.equals(sortParameter, "createdBy"))
            sortColumn = REPORT_CREATED_BY;
        else if (Objects.equals(sortParameter, "quantity"))
            sortColumn = REPORT_ITEMS_QUANTITY;
        else if (Objects.equals(sortParameter, "price"))
            sortColumn = REPORT_TOTAL_PRICE;

        return reportRepository.getLimit(total * (nOfPage - 1), total, sortColumn);
    }

    @Override
    public int getNumberOfRows() {
        return reportRepository.getNumberOfRows();
    }

    @Override
    public List<ReportElement> getAll() {
        return reportRepository.getAll();
    }

    @Override
    public boolean deleteAll() {
        return reportRepository.deleteAll();
    }

    @Override
    public List<ReportDTO> convertToDTO(List<ReportElement> report) {
        List<ReportDTO> result = new ArrayList<>();

        report.forEach(el -> result.add(new ReportDTO(report.indexOf(el) + 1, el.getCreatedBy(),
                el.getClosed_at().toLocalDateTime().toString().replace("T", " "),
                el.getItems_quantity(), String.format("%.2f", el.getTotal_price()))));
        return result;
    }
}
