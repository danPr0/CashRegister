package entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@AllArgsConstructor
@Getter @Setter
public class ReportElement {
    private int id;
    private String createdBy;
    private Timestamp closed_at;
    private int items_quantity;
    private double total_price;
}
