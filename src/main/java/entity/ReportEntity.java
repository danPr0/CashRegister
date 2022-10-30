package entity;

import lombok.*;

import java.sql.Timestamp;

/**
 * Entity for "report" table
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class ReportEntity {
    private int id;
    private int userId;
    private Timestamp closedAt;
    private int itemsQuantity;
    private double totalPrice;
}
