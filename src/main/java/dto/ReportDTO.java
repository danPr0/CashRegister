package dto;

import entity.ReportEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *  DTO for {@link ReportEntity}
 */
@AllArgsConstructor
@Getter @Setter
public class ReportDTO {
    private int index;
    private String createdBy;
    private String closedAt;
    private int itemsQuantity;
    private String totalPrice;
}
