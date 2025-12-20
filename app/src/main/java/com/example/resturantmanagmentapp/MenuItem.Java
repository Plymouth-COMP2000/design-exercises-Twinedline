package com.example.resturantmanagmentapp;

public class MenuItem {
    private String name;
    private double price;
    private String description;
    private String catagory;

    public MenuItem() {}

    public MenuItem(String name, double price, String description, String catagory) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.catagory = catagory;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getDescription() { return description; }
    public String getCatagory() { return catagory; }

    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
    public void setDescription(String description) { this.description = description; }
    public void setCatagory(String catagory) { this.catagory = catagory; }
}
