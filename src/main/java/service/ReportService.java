package service;

import dto.ReportDTO;
import entity.ReportEntity;
import util.enums.Language;
import util.table.ReportColumnName;

import java.util.List;

/**
 * Service layer for {@link dao.ReportDAO}
 */
public interface ReportService {
    boolean createReport(String username);
    /**
     * Implement pagination
     */
    List<ReportEntity> getPerPage(int nOfPage, int total, ReportColumnName sortBy, boolean ifAscending);
    int getNoOfRows();
    List<ReportEntity> getAll();
    boolean deleteAll();
    List<ReportDTO> convertToDTO(List<ReportEntity> report, Language lang);
}
