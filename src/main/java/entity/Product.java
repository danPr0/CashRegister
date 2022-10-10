package entity;

import util.ProductMeasure;

public class Product {
    private int id;
    private String name;
    private ProductMeasure measure;
    private double quantity;
    private double price;

    public Product(int id, String name, ProductMeasure measure, double quantity, double price) {
        this.id = id;
        this.name = name;
        this.measure = measure;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ProductMeasure getMeasure() {
        return measure;
    }

    public void setMeasure(ProductMeasure measure) {
        this.measure = measure;
    }
}
