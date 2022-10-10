package service;

import entity.Product;
import dao_impl.ProductRepository;
import util.ProductMeasure;
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

    public boolean addProduct(String productName, ProductMeasure measure, double quantity, double price) {
        if (!Validator.validateProductName(productName) || quantity < 0 || price <= 0 ||
                (measure.equals(ProductMeasure.apiece) && quantity % 1 != 0))
            return false;

        return productRepository.insertProduct(new Product(0, productName, measure, quantity, price));
    }

    public Product getProductById(int id) {
        return productRepository.getProductById(id);
    }

    public Product getProductByName(String name) {
        return productRepository.getProductByName(name);
    }

    public boolean updateProductById(int id, double quantity, double price) {
        Product product = productRepository.getProductById(id);
        return updateProduct(product, quantity, price);
    }

    public boolean updateProductByName(String name, int quantity, double price) {
        Product product = productRepository.getProductByName(name);
        return updateProduct(product, quantity, price);
    }

    public boolean updateProduct(Product product, double newQuantity, double newPrice) {
        if (product == null || newQuantity < 0 || newPrice <= 0 ||
                (product.getMeasure().equals(ProductMeasure.apiece) && newQuantity % 1 != 0))
            return false;

        product.setQuantity(newQuantity);
        product.setPrice(newPrice);

        return productRepository.updateProduct(product);
    }
}
