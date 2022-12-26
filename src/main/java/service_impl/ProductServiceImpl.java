package service_impl;

import dao.ProductDAO;
import dao.ProductTranslationDAO;
import dao_impl.ProductTranslationDAOImpl;
import dto.ProductDTO;
import entity.Product;
import dao_impl.ProductDAOImpl;
import entity.ProductTranslation;
import exception.ProductTranslationException;
import service.ProductService;
import util.enums.Language;
import util.enums.ProductMeasure;
import util.Validator;

import java.util.Map;

public class ProductServiceImpl implements ProductService {
    private static ProductServiceImpl instance = null;
    private ProductDAO productDAO = ProductDAOImpl.getInstance();
    private ProductTranslationDAO productTranslationDAO = ProductTranslationDAOImpl.getInstance();

    private ProductServiceImpl() {
    }

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
    public Product getProduct(String name, Language lang) {
        ProductTranslation productTranslation = productTranslationDAO.getEntityByProductName(name, lang);
        Product product = null;
        if (productTranslation != null) {
            product = productDAO.getEntityById(productTranslation.getProductId());
        }

        return product;
    }

    @Override
    public boolean addProduct(Map<Language, String> productNames, ProductMeasure measure, double quantity, double price) throws ProductTranslationException {
        if (!(Validator.validateProductName(productNames.get(Language.ua)) && Validator.validateProductName(productNames.get(Language.en))
                && Validator.validateQuantity(measure, quantity) && Validator.validatePrice(price)))
            return false;

        productDAO.insertEntity(new Product(0, productNames, measure, quantity, price), Language.en);
        return true;
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
    public ProductDTO convertToDTO(Product product, Language lang) {
        String quantity = String.format(product.getMeasure().equals(ProductMeasure.weight) ? "%.3f" : "%.0f", product.getQuantity());
        return new ProductDTO(product.getId(), product.getProductTranslations().get(lang), quantity, String.format("%.2f", product.getPrice()));
    }

    public void setProductDAO(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public void setProductTranslationDAO(ProductTranslationDAO productTranslationDAO) {
        this.productTranslationDAO = productTranslationDAO;
    }
}
