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

    public boolean updateProduct(String productName, int quantity, double price) {
        if (!Validator.validateProductName(productName) || quantity < 0 || price <= 0)
            return false;

        return productRepository.updateProduct(new Product(0, productName, quantity, price));
    }

    public Product getProduct(String name) {
        return productRepository.getProductByName(name);
    }
}
