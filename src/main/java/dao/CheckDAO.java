package dao;

import entity.CheckEntity;
import entity.Product;

import java.util.List;

/**
 * DAO layer for "check" table
 */
public interface CheckDAO {
    CheckEntity getEntityByProduct(Product product);
    boolean insertEntity(CheckEntity checkEntity);
    boolean updateEntity(CheckEntity checkEntity);
    boolean deleteEntityById(int id);
    /**
     * Get sorted and ordered segment of entities
     * @param order (ASC/DESC)
     */
    List<CheckEntity> getSegment(int offset, int limit, String sortColumn, String order);
    int getNoOfRows();
    List<CheckEntity> getAll();
    boolean deleteAll();
}
