package entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import util.ProductMeasure;

/**
 * Entity for "products" table
 */
@AllArgsConstructor
@Getter @Setter
public class Product {
    private int id;
    private String name;
    private ProductMeasure measure;
    private double quantity;
    private double price;
}
