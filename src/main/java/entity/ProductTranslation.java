package entity;

import lombok.*;
import util.enums.Language;

/**
 * Entity for "products_translations" table
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class ProductTranslation {
    private int productId;
    private Language langId;
    private String translation;
}
