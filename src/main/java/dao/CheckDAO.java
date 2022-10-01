package dao;

import entity.CheckElement;
import entity.Product;

import java.util.List;

public interface CheckDAO {
    CheckElement getCheckElementByProduct(Product product);
    CheckElement getCheckElementById(int id);
    boolean insertCheckElement(CheckElement checkElement);
    boolean updateCheckElement(CheckElement checkElement);
    boolean deleteCheckElementById(int id);
    List<CheckElement> getAll();
    boolean deleteAll();
    List<CheckElement> getLimit(int offset, int limit, String sortColumn);
    int getNumberOfRows();
}
