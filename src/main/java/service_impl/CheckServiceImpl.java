package service_impl;

import dto.CheckDTO;
import entity.CheckElement;
import entity.Product;
import dao_impl.CheckRepository;
import dao_impl.ProductRepository;
import service.CheckServiceInterface;
import util.ProductMeasure;
import util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static util.DBFields.*;

public class CheckServiceImpl implements CheckServiceInterface {
    private static CheckServiceImpl instance = null;
    private final CheckRepository checkRepository = CheckRepository.getInstance();
    private final ProductRepository productRepository = ProductRepository.getInstance();

    private CheckServiceImpl() {
    }

    public static CheckServiceImpl getInstance() {
        if (instance == null)
            instance = new CheckServiceImpl();
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

    @Override
    public boolean addToCheck(Product product, double quantity) {
        if (product == null || !Validator.validateQuantity(product.getMeasure(), quantity) || product.getQuantity() < quantity)
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

    @Override
    public boolean updateCheckByProductName(String name, int newQuantity) {
        Product product = productRepository.getProductByName(name);
        if (product == null)
            return false;

        CheckElement checkElement = checkRepository.getCheckElementByProduct(product);
        if (checkElement == null || !Validator.validateQuantity(product.getMeasure(), newQuantity) ||
                product.getQuantity() + checkElement.getQuantity() < newQuantity)
            return false;

        product.setQuantity(product.getQuantity() + checkElement.getQuantity() - newQuantity);
        checkElement.setQuantity(newQuantity);
        productRepository.updateProduct(product);

        return checkRepository.updateCheckElement(checkElement);
    }

//    public boolean updateCheckByProductName(Product product, CheckElement checkElement, int newQuantity) {
//        if (newQuantity < 0 || product.getQuantity() + checkElement.getQuantity() < newQuantity)
//            return false;
//
//        product.setQuantity(product.getQuantity() + checkElement.getQuantity() - newQuantity);
//        checkElement.setQuantity(newQuantity);
//        productRepository.updateProduct(product);
//
//        return checkRepository.updateProduct(checkElement);
//    }

    @Override
    public boolean closeCheck() {
        return checkRepository.deleteAll();
    }

    @Override
    public CheckElement getCheckElementByProductId(int id) {
        Product product = productRepository.getProductById(id);
        return checkRepository.getCheckElementByProduct(product);
    }

    @Override
    public CheckElement getCheckElementByProductName(String name) {
        Product product = productRepository.getProductByName(name);
        return checkRepository.getCheckElementByProduct(product);
    }

    @Override
    public boolean cancelCheckElement(CheckElement checkElement, double quantity) {
        if (checkElement == null || !Validator.validateQuantity(checkElement.getProduct().getMeasure(), quantity) ||
                checkElement.getQuantity() < quantity)
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

    @Override
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

    @Override
    public List<CheckElement> getAll() {
        return checkRepository.getAll();
    }

    @Override
    public List<CheckElement> getPerPage(int nOfPage, int total, String sortBy) {
        if (Objects.equals(sortBy, "productId"))
            sortBy = CHECK_PRODUCT_ID;
        else if (Objects.equals(sortBy, "quantity"))
            sortBy = CHECK_PRODUCT_QUANTITY;
        else sortBy = CHECK_ID;

        return checkRepository.getLimit(total * (nOfPage - 1), total, sortBy);
    }

    @Override
    public int getNumberOfRows() {
        return checkRepository.getNumberOfRows();
    }

    @Override
    public List<CheckDTO> convertToDTO(List<CheckElement> check) {
        List<CheckDTO> result = new ArrayList<>();

        check.forEach(el -> {
            Product product = el.getProduct();
            String quantity = String.format(product.getMeasure().equals(ProductMeasure.weight) ? "%.3f" : "%.0f", el.getQuantity());
            result.add(new CheckDTO(check.indexOf(el) + 1, product.getId(), product.getName(), quantity,
                    String.format("%.2f", product.getPrice()), String.format("%.2f", product.getPrice() * el.getQuantity())));
        });
        return result;
    }
}
