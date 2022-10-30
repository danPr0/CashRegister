package dao;

import entity.Product;
import exception.ProductTranslationException;
import util.enums.Language;

import java.sql.Connection;

/**
 * DAO layer for "products" table
 */
public interface ProductDAO {
    /**
     * Method gets product entity and bounds its translations from "products_translation" in the another transaction
     */
    Product getEntityById(int id);
    /**
     * Method handle 2 transactions(insert product in "products" table and insert product translations in "products_translations" table
     */
    void insertEntity(Product product, Language defaultLang) throws ProductTranslationException;
    boolean updateEntity(Product product);
}
