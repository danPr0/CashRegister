package entity;

import lombok.*;

/**
 * Entity for "check" table
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class CheckEntity {
    private int id;
    private int productId;
    private double quantity;
}
