package dao;

import entity.Product;

/**
 * DAO layer for "products" table
 */
public interface ProductDAO {
    Product getEntityById(int id);
    Product getEntityByName(String name);
    boolean insertEntity(Product product);
    boolean updateEntity(Product product);
}
