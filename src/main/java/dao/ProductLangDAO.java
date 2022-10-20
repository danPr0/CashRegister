package dao;

import entity.Product;
import entity.ProductLang;
import util.enums.Language;

import java.sql.Connection;

public interface ProductLangDAO {
    boolean insertEntity(ProductLang entity, Connection connection);
    ProductLang getEntityByProductName(String productName, Language langId);
//    String getProductName(int productId, int langId);
}
