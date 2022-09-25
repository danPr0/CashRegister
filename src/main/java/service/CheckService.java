package service;

import entity.CheckElement;
import entity.Product;
import dao_impl.CheckRepository;
import dao_impl.ProductRepository;

import java.util.List;

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

//    public boolean specifyCheckByProductId(String productId, String quantity) {
//        boolean result = false;
//        try {
//            Product product = productRepository.getProductById(Integer.parseInt(productId));
//            result = specifyCheck(product, Integer.parseInt(quantity));
//        }
//        catch (NullPointerException | NumberFormatException e) {
//            e.printStackTrace();
//        }
//
//        return result;
//    }
//
//    public boolean specifyCheckByProductName(String productName, String quantity) {
//        Product product = productRepository.getProductByName(productName);
//        boolean result = false;
//
//        try {
//            result = specifyCheck(product, Integer.parseInt(quantity));
//        }
//        catch (NullPointerException | NumberFormatException e) {
//            e.printStackTrace();
//        }
//
//        return result;
//    }

    public boolean specifyCheckByProductId(int productId, int quantity) {
        Product product = productRepository.getProductById(productId);
        return specifyCheck(product, quantity);
    }

    public boolean specifyCheckByProductName(String productName, int quantity) {
        Product product = productRepository.getProductByName(productName);
        return specifyCheck(product, quantity);
    }

    public boolean specifyCheck(Product product, int quantity) {
        if (product == null)
            return false;

        CheckElement checkElement = checkRepository.getCheckElementByProduct(product);
        if (quantity < 0 || product.getQuantity() < quantity)
            return false;

        if (checkElement == null) {
            product.setQuantity(product.getQuantity() - quantity);
            productRepository.updateProduct(product);
            return checkRepository.insertCheckElement(new CheckElement(0, product, quantity));
        }
        else {
            product.setQuantity(product.getQuantity() + checkElement.getQuantity() - quantity);
            checkElement.setQuantity(quantity);
            productRepository.updateProduct(product);
            return checkRepository.updateCheckElement(checkElement);
        }
    }

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

    public boolean cancelCheckElementById(int id, int quantity) {
        Product product = productRepository.getProductById(id);
        return cancelCheckElement(product, quantity);
    }

    public boolean cancelCheckElementByName(String productName, int quantity) {
        Product product = productRepository.getProductByName(productName);
        return cancelCheckElement(product, quantity);
    }
    public boolean cancelCheckElement(Product product, int quantity) {
        if (product == null)
            return false;

        CheckElement checkElement = checkRepository.getCheckElementByProduct(product);
        if (checkElement == null || checkElement.getQuantity() < quantity)
            return false;

        product.setQuantity(product.getQuantity() + quantity);
        productRepository.updateProduct(product);
        if (checkElement.getQuantity() == quantity) {
            return checkRepository.deleteCheckElementById(checkElement.getId());
        }
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

    public List<CheckElement> getPerPage(int nOfPage, int total) {
        return checkRepository.getLimit(total * (nOfPage - 1), total);
    }

    public int getNumberOfRows() {
        return checkRepository.getNumberOfRows();
    }
}
