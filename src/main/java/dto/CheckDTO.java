package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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
