package service;

import dto.CheckDTO;
import entity.CheckEntity;
import entity.Product;
import util.enums.Language;
import util.table.CheckColumnName;

import java.util.List;

/**
 * Service layer for {@link dao.CheckDAO}
 */
public interface CheckService {
    CheckEntity getCheckElement(int productId);
    CheckEntity getCheckElement(String productName, Language lang);
    boolean addToCheck(Product product, double quantity);
    boolean closeCheck();
    boolean cancelCheckElement(CheckEntity checkEntity, double quantity);
    boolean cancelCheck();
    /**
     * Implement pagination
     */
    List<CheckEntity> getPerPage(int nOfPage, int total, CheckColumnName sortBy, boolean ifAscending);
    int getNumberOfRows();
    List<CheckDTO> convertToDTO(List<CheckEntity> check, Language lang);
}
