package entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class CheckElement {
    private int id;
    private Product product;
    private double quantity;
}
