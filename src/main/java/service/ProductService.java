package service;

import dto.ProductDTO;
import entity.Product;
import util.ProductMeasure;

/**
 * Service layer for {@link dao.ProductDAO}
 */
public interface ProductService {
    Product getProduct(int id);
    Product getProduct(String name);
    boolean addProduct(String productName, ProductMeasure measure, double quantity, double price);
    boolean updateProduct(int productId, double newQuantity, double newPrice);
    ProductDTO convertToDTO(Product product);
}
