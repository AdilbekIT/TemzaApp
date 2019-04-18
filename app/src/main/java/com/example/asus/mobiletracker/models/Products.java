package com.example.asus.mobiletracker.models;

public class Products {

    private int Id;
    private String name_of_product;
    private double price;
    private int serial_number;
    private String description;
    private int views;
    private int created_at;

    public Products(int id, String name_of_product, double price, int serial_number, String description, int views, int created_at) {
        Id = id;
        this.name_of_product = name_of_product;
        this.price = price;
        this.serial_number = serial_number;
        this.description = description;
        this.views = views;
        this.created_at = created_at;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName_of_product() {
        return name_of_product;
    }

    public void setName_of_product(String name_of_product) {
        this.name_of_product = name_of_product;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(int serial_number) {
        this.serial_number = serial_number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getCreated_at() {
        return created_at;
    }

    public void setCreated_at(int created_at) {
        this.created_at = created_at;
    }
}
