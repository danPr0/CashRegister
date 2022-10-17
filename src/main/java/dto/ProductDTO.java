package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *  DTO for {@link entity.Product}
 */
@AllArgsConstructor
@Getter @Setter
public class ProductDTO {
    private int id;
    private String name;
    private String quantity;
    private String price;
}
