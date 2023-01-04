package dao;

import entity.CheckEntity;

import java.util.List;

/**
 * DAO layer for "check" table
 */
public interface CheckDAO {
    CheckEntity getEntityByProductId(int productId);
    boolean insertEntity(CheckEntity checkEntity);
    boolean updateEntity(CheckEntity checkEntity);
    boolean deleteEntityByProductId(int productId);
    /**
     * Get sorted and ordered segment of entities
     * @param order (ASC/DESC)
     */
    List<CheckEntity> getSegment(int offset, int limit, String sortColumn, String order);
    int getNoOfRows();
    List<CheckEntity> getAll();
    boolean deleteAll();
}
