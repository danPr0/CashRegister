package entity;

import java.sql.Date;

public class ReportElement {
    private int id;
    private String username;
    private Date closed_at;
    private int items_quantity;
    private double total_price;

    public ReportElement(int id, String username, Date closed_at, int items_quantity, double total_price) {
        this.id = id;
        this.username = username;
        this.closed_at = closed_at;
        this.items_quantity = items_quantity;
        this.total_price = total_price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getClosed_at() {
        return closed_at;
    }

    public void setClosed_at(Date closed_at) {
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
