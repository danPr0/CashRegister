package dao;

import entity.ProductTranslation;
import util.enums.Language;

/**
 * DAO layer for "products_translations" table
 */
public interface ProductTranslationDAO {
    //    boolean insertEntity(ProductTranslation entity, Connection connection);
    ProductTranslation getEntityByProductName(String productName, Language langId);
//    String getProductName(int productId, int langId);
}
