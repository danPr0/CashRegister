package dto;

import entity.CheckEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *  DTO for {@link CheckEntity}
 */
@AllArgsConstructor
@Getter @Setter
public class CheckDTO {
    private int index;
    private int productId;
    private String productName;
    private String quantity;
    private String productPrice;
    private String totalPrice;
}
