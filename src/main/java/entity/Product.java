package entity;

import lombok.*;
import util.enums.Language;
import util.enums.ProductMeasure;

import java.util.Map;

/**
 * Entity for "products" table
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class Product {
    private int id;
    private Map<Language, String> productTranslations;
    private ProductMeasure measure;
    private double quantity;
    private double price;
}
