package service;

import dto.ReportDTO;
import entity.ReportEntity;

import java.util.List;

/**
 * Service layer for {@link dao.ReportDAO}
 */
public interface ReportService {
    boolean createReport(String username);
    /**
     * Implement pagination
     */
    List<ReportEntity> getPerPage(int nOfPage, int total, String sortParameter, boolean ifAscending);
    int getNoOfRows();
    List<ReportEntity> getAll();
    boolean deleteAll();
    List<ReportDTO> convertToDTO(List<ReportEntity> report);
}
