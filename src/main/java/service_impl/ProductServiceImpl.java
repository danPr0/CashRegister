package service_impl;

import dao.ProductDAO;
import dao.ProductLangDAO;
import dao_impl.CheckDAOImpl;
import dao_impl.ProductLangDAOImpl;
import dto.ProductDTO;
import entity.Product;
import dao_impl.ProductDAOImpl;
import entity.ProductLang;
import exception.ProductTranslationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ProductService;
import util.enums.Language;
import util.enums.ProductMeasure;
import util.Validator;
import util.db.ConnectionFactory;

import java.util.Map;

public class ProductServiceImpl implements ProductService {
    private static ProductServiceImpl instance = null;
    private final ProductDAO productDAO = ProductDAOImpl.getInstance();
    private final ProductLangDAO productLangDAO = ProductLangDAOImpl.getInstance();
    //    private final LangDAO langDAO = LangDAOImpl.getInstance();
    private final ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
    private final Logger logger = LogManager.getLogger(CheckDAOImpl.class);

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
        ProductLang productLang = productLangDAO.getEntityByProductName(name, lang);
        Product product = null;
        if (productLang != null) {
            product = productDAO.getEntityById(productLang.getProductId());
        }

        return product;
    }

    @Override
    public boolean addProduct(Language lang, Map<Language, String> productNames, ProductMeasure measure, double quantity, double price) throws ProductTranslationException {
        if (!(Validator.validateProductName(productNames.get(Language.ua)) && Validator.validateProductName(productNames.get(Language.en))
                && Validator.validateQuantity(measure, quantity) && Validator.validatePrice(price)))
            return false;

//        String error = null;
//        try (Connection connection = connectionFactory.getConnection()) {
//            connection.setAutoCommit(false);
//            int productId = productDAO.insertEntity(new Product(0, productNames.get("en"), measure, quantity, price), connection);
//            if (productId == 0) {
//                connection.close();
//                return "msg.error.commodity-expert.addProductEN";
//            }
//
//            Map<String, String> incorrectNames = new HashMap<>();
//            productNames.forEach((k, v) -> {
//                int langId = langDAO.getEntityByVar(k).getId();
//                if (productLangDAO.getProductName(productId, langDAO.getEntityByVar(k).getId()) != null ||
//                        !productLangDAO.insertEntity(new ProductLang(productId, langId, v), connection)) {
//                    if (k.equals("ua"))
//                        incorrectNames.put(k, "msg.error.commodity-expert.addProductUA");
//                    else incorrectNames.put(k, "msg.error.commodity-expert.addProductEN");
//                }
//            });
//
//            if (incorrectNames.isEmpty())
//                connection.commit();
//            else {
//                connection.rollback();
//                StringBuilder result = new StringBuilder();
//                incorrectNames.values().forEach(v -> {
//                    try {
//                        result.append(GetProperties.getMessageByLang(v, lang)).append("\n");
//                    } catch (IOException e) {
//                        logger.error("Cannot read error in properties", e);
//                    }
//                });
//                error = result.toString();
//            }
//        } catch (SQLException e) {
//            logger.error("Cannot add product", e);
//        }
        return productDAO.insertEntity(new Product(0, productNames, measure, quantity, price), Language.en);
//        String error = null;
//        try {
//            if (langError != null)
//                error = GetProperties.getMessageByLang("msg.error.commodity-expert.addProduct_" + langError, lang.toString());
//        } catch (IOException e) {
//            logger.error("Cannot read error in properties", e);
//        }
//        return error;
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
}
