package dto;

import java.sql.Timestamp;

public class ReportDTO {
    private String createdBy;
    private String closed_at;
    private int items_quantity;
    private double total_price;

    public ReportDTO(String createdBy, String closed_at, int items_quantity, double total_price) {
        this.createdBy = createdBy;
        this.closed_at = closed_at;
        this.items_quantity = items_quantity;
        this.total_price = total_price;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getClosed_at() {
        return closed_at;
    }

    public void setClosed_at(String closed_at) {
        this.closed_at = closed_at;
    }

    public int getItems_quantity() {
        return items_quantity;
    }

    public void setItems_quantity(int items_quantity) {
        this.items_quantity = items_quantity;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }
}
