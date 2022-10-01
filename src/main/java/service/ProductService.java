package service;

import entity.Product;
import dao_impl.ProductRepository;
import util.Validator;

public class ProductService {
    private static ProductService instance = null;
    private final ProductRepository productRepository = ProductRepository.getInstance();

    private ProductService() {}

    public static ProductService getInstance() {
        if (instance == null)
            instance = new ProductService();
        return instance;
    }

    public boolean addProduct(String productName, int quantity, double price) {
        if (!Validator.validateProductName(productName) || quantity < 0 || price <= 0)
            return false;

        return productRepository.insertProduct(new Product(0, productName, quantity, price));
    }

    public Product getProductById(int id) {
        return productRepository.getProductById(id);
    }

    public Product getProductByName(String name) {
        return productRepository.getProductByName(name);
    }

    public boolean updateProductById(int id, int quantity, double price) {
        Product product = productRepository.getProductById(id);
        return updateProduct(product, quantity, price);
    }

    public boolean updateProductByName(String name, int quantity, double price) {
        Product product = productRepository.getProductByName(name);
        return updateProduct(product, quantity, price);
    }

    public boolean updateProduct(Product product, int newQuantity, double newPrice) {
        if (product == null || newQuantity < 0 || newPrice <= 0)
            return false;

        product.setQuantity(newQuantity);
        product.setPrice(newPrice);

        return productRepository.updateProduct(product);
    }
}
