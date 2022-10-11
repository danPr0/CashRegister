package service;

import dto.ProductDTO;
import entity.Product;
import util.ProductMeasure;

public interface ProductServiceInterface {
    boolean addProduct(String productName, ProductMeasure measure, double quantity, double price);
    Product getProductById(int id);
    Product getProductByName(String name);
    boolean updateProductById(int id, double quantity, double price);
    ProductDTO convertToDTO(Product product);
}
