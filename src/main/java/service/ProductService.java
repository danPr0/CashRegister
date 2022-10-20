package service;

import dto.ProductDTO;
import entity.Product;
import exception.ProductTranslationException;
import util.enums.Language;
import util.enums.ProductMeasure;

import java.util.Map;

/**
 * Service layer for {@link dao.ProductDAO}
 */
public interface ProductService {
    Product getProduct(int id);
    Product getProduct(String name, Language lang);
    boolean addProduct(Language lang, Map<Language, String> names, ProductMeasure measure, double quantity, double price)
            throws ProductTranslationException;
    boolean updateProduct(int productId, double newQuantity, double newPrice);
    ProductDTO convertToDTO(Product product, Language lang);
}
