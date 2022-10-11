package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class ReportDTO {
    private int index;
    private String createdBy;
    private String closedAt;
    private int itemsQuantity;
    private String totalPrice;
}
