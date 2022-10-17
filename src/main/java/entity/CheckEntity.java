package entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity for "check" table
 */
@AllArgsConstructor
@Getter @Setter
public class CheckEntity {
    private int id;
    private Product product;
    private double quantity;
}
