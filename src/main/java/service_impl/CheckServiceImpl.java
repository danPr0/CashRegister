package service_impl;

import dao.CheckDAO;
import dao.ProductDAO;
import dao.ProductLangDAO;
import dao_impl.ProductLangDAOImpl;
import dto.CheckDTO;
import entity.CheckEntity;
import entity.Product;
import dao_impl.CheckDAOImpl;
import dao_impl.ProductDAOImpl;
import service.CheckService;
import util.enums.Language;
import util.enums.ProductMeasure;
import util.Validator;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static util.db.DBFields.*;

public class CheckServiceImpl implements CheckService {
    private static CheckServiceImpl instance = null;
    private final CheckDAO checkDAO = CheckDAOImpl.getInstance();
    private final ProductDAO productDAO = ProductDAOImpl.getInstance();
    private final ProductLangDAO productLangDAO = ProductLangDAOImpl.getInstance();
//    private final LangDAO langDAO = LangDAOImpl.getInstance();

    private CheckServiceImpl() {
    }

    public static CheckServiceImpl getInstance() {
        if (instance == null)
            instance = new CheckServiceImpl();
        return instance;
    }

    @Override
    public CheckEntity getCheckElement(int id) {
        Product product = productDAO.getEntityById(id);
        return checkDAO.getEntityByProduct(product);
    }

    @Override
    public CheckEntity getCheckElement(String productName, Language langId) {
        int productId = productLangDAO.getEntityByProductName(productName, langId).getProductId();
        Product product = productDAO.getEntityById(productId);
        return checkDAO.getEntityByProduct(product);
    }

    @Override
    public boolean addToCheck(Product product, double quantity) {
        if (product == null || !Validator.validateQuantity(product.getMeasure(), quantity) || product.getQuantity() < quantity)
            return false;

        CheckEntity checkEntity = checkDAO.getEntityByProduct(product);
        product.setQuantity(product.getQuantity() - quantity);
        productDAO.updateEntity(product);
        if (checkEntity == null)
            return checkDAO.insertEntity(new CheckEntity(0, product.getId(), quantity));
        else {
            checkEntity.setQuantity(checkEntity.getQuantity() + quantity);
            return checkDAO.updateEntity(checkEntity);
        }
    }

    @Override
    public boolean closeCheck() {
        return checkDAO.deleteAll();
    }

    @Override
    public boolean cancelCheckElement(CheckEntity checkEntity, double quantity) {
        Product product;
        if (checkEntity == null || (product = productDAO.getEntityById(checkEntity.getProductId())) == null
                || !Validator.validateQuantity(product.getMeasure(), quantity) ||
                checkEntity.getQuantity() < quantity)
            return false;

        product.setQuantity(product.getQuantity() + quantity);
        productDAO.updateEntity(product);
        if (checkEntity.getQuantity() == quantity)
            return checkDAO.deleteEntityById(checkEntity.getId());
        else {
            checkEntity.setQuantity(checkEntity.getQuantity() - quantity);
            return checkDAO.updateEntity(checkEntity);
        }
    }

    @Override
    public boolean cancelCheck() {
        List<CheckEntity> productsInCheckEntity = checkDAO.getAll();
        if (productsInCheckEntity == null)
            return false;

        for (CheckEntity productInCheckEntity : productsInCheckEntity) {
            Product product = productDAO.getEntityById(productInCheckEntity.getProductId());
            product.setQuantity(product.getQuantity() + productInCheckEntity.getQuantity());
            productDAO.updateEntity(product);
        }

        return checkDAO.deleteAll();
    }

    @Override
    public List<CheckEntity> getPerPage(int nOfPage, int total, String sortBy, boolean ifAscending) {
        String sortColumn = CHECK_ID;
        if (Objects.equals(sortBy, "product"))
            sortColumn = CHECK_PRODUCT_ID;
        else if (Objects.equals(sortBy, "quantity"))
            sortColumn = CHECK_PRODUCT_QUANTITY;

        return checkDAO.getSegment(total * (nOfPage - 1), total, sortColumn, ifAscending ? "ASC" : "DESC");
    }

    @Override
    public int getNumberOfRows() {
        return checkDAO.getNoOfRows();
    }

    @Override
    public List<CheckDTO> convertToDTO(List<CheckEntity> check, Language lang) {
        List<CheckDTO> result = new ArrayList<>();

        check.forEach(el -> {
            Product product = productDAO.getEntityById(el.getProductId());
//            product.setName(productLangDAO.getProductName(product.getId(), langDAO.getEntityByVar(lang).getId()));
            String quantity = String.format(product.getMeasure().equals(ProductMeasure.weight) ? "%.3f" : "%.0f", el.getQuantity());
            result.add(new CheckDTO(check.indexOf(el) + 1, product.getId(), product.getProductTranslations().get(lang), quantity,
                    NumberFormat.getCurrencyInstance(new Locale("uk", "UA")).format(product.getPrice()),
                    NumberFormat.getCurrencyInstance(new Locale("uk", "UA")).format(product.getPrice() * el.getQuantity())));
        });
        return result;
    }
}
