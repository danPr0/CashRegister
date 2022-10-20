package entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * Entity for "report" table
 */
@AllArgsConstructor
@Getter @Setter
public class ReportEntity {
    private int id;
    private int userId;
    private Timestamp closed_at;
    private int items_quantity;
    private double total_price;
}
