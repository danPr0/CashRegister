package service_impl;

import dao.ProductDAO;
import dto.ProductDTO;
import entity.Product;
import dao_impl.ProductDAOImpl;
import service.ProductService;
import util.ProductMeasure;
import util.Validator;

public class ProductServiceImpl implements ProductService {
    private static ProductServiceImpl instance = null;
    private final ProductDAO productDAO = ProductDAOImpl.getInstance();

    private ProductServiceImpl() {}

    public static ProductServiceImpl getInstance() {
        if (instance == null)
            instance = new ProductServiceImpl();
        return instance;
    }

    @Override
    public Product getProduct(int id) {
        return productDAO.getEntityById(id);
    }

    @Override
    public Product getProduct(String name) {
        return productDAO.getEntityByName(name);
    }

    @Override
    public boolean addProduct(String productName, ProductMeasure measure, double quantity, double price) {
        if (!(Validator.validateProductName(productName) && Validator.validateQuantity(measure, quantity)
                && Validator.validatePrice(price)))
            return false;

        return productDAO.insertEntity(new Product(0, productName, measure, quantity, price));
    }

    @Override
    public boolean updateProduct(int productId, double newQuantity, double newPrice) {
        Product product = productDAO.getEntityById(productId);
        if (product == null || !(Validator.validateQuantity(product.getMeasure(), newQuantity)
                && Validator.validatePrice(newPrice)))
            return false;

        product.setQuantity(newQuantity);
        product.setPrice(newPrice);

        return productDAO.updateEntity(product);
    }

    @Override
    public ProductDTO convertToDTO(Product product) {
        String quantity = String.format(product.getMeasure().equals(ProductMeasure.weight) ? "%.3f" : "%.0f", product.getQuantity());
        return new ProductDTO(product.getId(), product.getName(), quantity, String.format("%.2f", product.getPrice()));
    }
}
