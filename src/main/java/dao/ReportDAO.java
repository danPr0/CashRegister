package dao;

import entity.ReportEntity;

import java.util.List;

/**
 * DAO layer for "report" table
 */
public interface ReportDAO {
    boolean insertEntity(ReportEntity reportEntity);
    /**
     * Get sorted and ordered segment of entities
     * @param order (ASC/DESC)
     */
    List<ReportEntity> getSegment(int offset, int limit, String sortColumn, String order);
    int getNoOfRows();
    List<ReportEntity> getAll();
    boolean deleteAll();
}
