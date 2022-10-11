package service;

import dto.CheckDTO;
import entity.CheckElement;
import entity.Product;

import java.util.List;

public interface CheckServiceInterface {
    CheckElement getCheckElementByProductId(int id);
    CheckElement getCheckElementByProductName(String name);
    boolean addToCheck(Product product, double quantity);
    boolean updateCheckByProductName(String name, int newQuantity);
    boolean closeCheck();
    boolean cancelCheckElement(CheckElement checkElement, double quantity);
    boolean cancelCheck();
    List<CheckElement> getAll();
    List<CheckElement> getPerPage(int nOfPage, int total, String sortBy);
    int getNumberOfRows();
    List<CheckDTO> convertToDTO(List<CheckElement> check);
}
