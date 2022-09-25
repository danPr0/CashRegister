package dao;

import entity.Product;

public interface ProductDAO {
    Product getProductByName(String name);
    Product getProductById(int id);
    boolean insertProduct(Product product);
    boolean updateProduct(Product product);
    boolean deleteProduct(int id);
}
