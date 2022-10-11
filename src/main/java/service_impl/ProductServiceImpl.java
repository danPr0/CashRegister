package service_impl;

import dto.ProductDTO;
import entity.Product;
import dao_impl.ProductRepository;
import service.ProductServiceInterface;
import util.ProductMeasure;
import util.Validator;

public class ProductServiceImpl implements ProductServiceInterface {
    private static ProductServiceImpl instance = null;
    private final ProductRepository productRepository = ProductRepository.getInstance();

    private ProductServiceImpl() {}

    public static ProductServiceImpl getInstance() {
        if (instance == null)
            instance = new ProductServiceImpl();
        return instance;
    }

    @Override
    public boolean addProduct(String productName, ProductMeasure measure, double quantity, double price) {
        if (!(Validator.validateProductName(productName) && Validator.validateQuantity(measure, quantity)
                && Validator.validatePrice(price)))
            return false;

        return productRepository.insertProduct(new Product(0, productName, measure, quantity, price));
    }

    @Override
    public Product getProductById(int id) {
        return productRepository.getProductById(id);
    }

    @Override
    public Product getProductByName(String name) {
        return productRepository.getProductByName(name);
    }

    @Override
    public boolean updateProductById(int id, double quantity, double price) {
        Product product = productRepository.getProductById(id);
        return updateProduct(product, quantity, price);
    }

    public boolean updateProductByName(String name, int quantity, double price) {
        Product product = productRepository.getProductByName(name);
        return updateProduct(product, quantity, price);
    }

    private boolean updateProduct(Product product, double newQuantity, double newPrice) {
        if (product == null || !(Validator.validateQuantity(product.getMeasure(), newQuantity)
                && Validator.validatePrice(newPrice)))
            return false;

        product.setQuantity(newQuantity);
        product.setPrice(newPrice);

        return productRepository.updateProduct(product);
    }

    @Override
    public ProductDTO convertToDTO(Product product) {
        String quantity = String.format(product.getMeasure().equals(ProductMeasure.weight) ? "%.3f" : "%.0f", product.getQuantity());
        return new ProductDTO(product.getId(), product.getName(), quantity, String.format("%.2f", product.getPrice()));
    }
}
