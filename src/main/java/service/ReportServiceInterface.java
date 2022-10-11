package service;

import dto.ReportDTO;
import entity.ReportElement;

import java.util.List;

public interface ReportServiceInterface {
    boolean add(String username);
    List<ReportElement> getPerPage(int nOfPage, int total, String sortParameter);
    int getNumberOfRows();
    List<ReportElement> getAll();
    boolean deleteAll();
    List<ReportDTO> convertToDTO(List<ReportElement> report);
}
