package dao;

import entity.Product;
import exception.ProductTranslationException;
import util.enums.Language;

import java.sql.Connection;

/**
 * DAO layer for "products" table
 */
public interface ProductDAO {
    Product getEntityById(int id);
//    Product getEntityByName(String name);
    boolean insertEntity(Product product, Language defaultLang) throws ProductTranslationException;
    boolean updateEntity(Product product);
    boolean deleteEntity(int id);
}
