package dao;

import dto.ReportDTO;
import entity.CheckElement;
import entity.ReportElement;

import java.util.List;

public interface ReportDAO {
    boolean insertReportElement(ReportElement reportElement);
    List<ReportElement> getAll();
    boolean deleteAll();
    int getNumberOfRows();
    List<ReportDTO> getLimit(int offset, int limit, String sortColumn);
}
