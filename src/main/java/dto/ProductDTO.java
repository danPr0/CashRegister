package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class ProductDTO {
    private int id;
    private String name;
    private String quantity;
    private String price;
}
