package service;

import entity.CheckElement;
import entity.Product;
import dao_impl.CheckRepository;
import dao_impl.ProductRepository;
import util.ProductMeasure;

import java.util.List;
import java.util.Objects;

import static util.DBFields.*;

public class CheckService {
    private static CheckService instance = null;
    private final CheckRepository checkRepository = CheckRepository.getInstance();
    private final ProductRepository productRepository = ProductRepository.getInstance();

    private CheckService() {
    }

    public static CheckService getInstance() {
        if (instance == null)
            instance = new CheckService();
        return instance;
    }

//    public boolean addToCheckByProductId(String productId, String quantity) {
//        boolean result = false;
//        try {
//            Product product = productRepository.getProductById(Integer.parseInt(productId));
//            result = addToCheck(product, Integer.parseInt(quantity));
//        }
//        catch (NullPointerException | NumberFormatException e) {
//            e.printStackTrace();
//        }
//
//        return result;
//    }
//
//    public boolean addToCheckByProductName(String productName, String quantity) {
//        Product product = productRepository.getProductByName(productName);
//        boolean result = false;
//
//        try {
//            result = addToCheck(product, Integer.parseInt(quantity));
//        }
//        catch (NullPointerException | NumberFormatException e) {
//            e.printStackTrace();
//        }
//
//        return result;
//    }

    public boolean addToCheckByProductId(int productId, int quantity) {
        Product product = productRepository.getProductById(productId);
        return addToCheck(product, quantity);
    }

    public boolean addToCheckByProductName(String productName, int quantity) {
        Product product = productRepository.getProductByName(productName);
        return addToCheck(product, quantity);
    }

    public boolean addToCheck(Product product, double quantity) {
        if (product == null || quantity <= 0 || product.getQuantity() < quantity ||
                (product.getMeasure().equals(ProductMeasure.apiece) && quantity % 1 != 0))
            return false;

        CheckElement checkElement = checkRepository.getCheckElementByProduct(product);
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.updateProduct(product);
        if (checkElement == null)
            return checkRepository.insertCheckElement(new CheckElement(0, product, quantity));
        else {
//            product.setQuantity(product.getQuantity() + checkElement.getQuantity() - quantity);
            checkElement.setQuantity(checkElement.getQuantity() + quantity);
            return checkRepository.updateCheckElement(checkElement);
        }
    }

//    public boolean addToCheckByProductId(int productId, int quantity) {
//        Product product = productRepository.getProductById(productId);
//        return addToCheck(product, quantity);
//    }
//
//    public boolean addToCheckByProductName(String productName, int quantity) {
//        Product product = productRepository.getProductByName(productName);
//        return addToCheck(product, quantity);
//    }
//
//    public boolean addToCheck(Product product, int quantity) {
//        if (quantity <= 0 || product.getQuantity() < quantity)
//            return false;
//
//        CheckElement checkElement = checkRepository.getCheckElementByProduct(product);
//        if (checkElement == null) {
//            product.setQuantity(product.getQuantity() - quantity);
//            productRepository.updateProduct(product);
//            return checkRepository.insertCheckElement(new CheckElement(0, product, quantity));
//        }
//    }

    public boolean updateCheck(String name, int newQuantity) {
        Product product = productRepository.getProductByName(name);
        if (product == null)
            return false;

        CheckElement checkElement = checkRepository.getCheckElementByProduct(product);
        if (checkElement == null || newQuantity < 0 || product.getQuantity() + checkElement.getQuantity() < newQuantity)
            return false;

        product.setQuantity(product.getQuantity() + checkElement.getQuantity() - newQuantity);
        checkElement.setQuantity(newQuantity);
        productRepository.updateProduct(product);

        return checkRepository.updateCheckElement(checkElement);
    }

//    public boolean updateCheck(Product product, CheckElement checkElement, int newQuantity) {
//        if (newQuantity < 0 || product.getQuantity() + checkElement.getQuantity() < newQuantity)
//            return false;
//
//        product.setQuantity(product.getQuantity() + checkElement.getQuantity() - newQuantity);
//        checkElement.setQuantity(newQuantity);
//        productRepository.updateProduct(product);
//
//        return checkRepository.updateProduct(checkElement);
//    }

    public boolean closeCheck() {
        return checkRepository.deleteAll();
    }

    public CheckElement getCheckElementByProductId(int id) {
        Product product = productRepository.getProductById(id);
        return checkRepository.getCheckElementByProduct(product);
    }

    public CheckElement getCheckElementByProductName(String name) {
        Product product = productRepository.getProductByName(name);
        return checkRepository.getCheckElementByProduct(product);
    }

    public boolean cancelCheckElement(CheckElement checkElement, double quantity) {
        if (checkElement == null || checkElement.getQuantity() < quantity ||
                (checkElement.getProduct().getMeasure().equals(ProductMeasure.apiece) && quantity % 1 != 0))
            return false;

        Product product = checkElement.getProduct();
        product.setQuantity(product.getQuantity() + quantity);
        productRepository.updateProduct(product);
        if (checkElement.getQuantity() == quantity)
            return checkRepository.deleteCheckElementById(checkElement.getId());
        else {
            checkElement.setQuantity(checkElement.getQuantity() - quantity);
            return checkRepository.updateCheckElement(checkElement);
        }
    }

    public boolean cancelCheck() {
        List<CheckElement> productsInCheckElement = checkRepository.getAll();
        if (productsInCheckElement == null)
            return false;

        for (CheckElement productInCheckElement : productsInCheckElement) {
            Product product = productInCheckElement.getProduct();
            product.setQuantity(product.getQuantity() + productInCheckElement.getQuantity());
            productRepository.updateProduct(product);
        }

        return checkRepository.deleteAll();
    }

    public List<CheckElement> getAll() {
        return checkRepository.getAll();
    }

    public List<CheckElement> getPerPage(int nOfPage, int total, String sortBy) {
        if (Objects.equals(sortBy, "productId"))
            sortBy = CHECK_PRODUCT_ID;
        else if (Objects.equals(sortBy, "quantity"))
            sortBy = CHECK_PRODUCT_QUANTITY;
        else sortBy = CHECK_ID;

        return checkRepository.getLimit(total * (nOfPage - 1), total, sortBy);
    }

    public int getNumberOfRows() {
        return checkRepository.getNumberOfRows();
    }
}
