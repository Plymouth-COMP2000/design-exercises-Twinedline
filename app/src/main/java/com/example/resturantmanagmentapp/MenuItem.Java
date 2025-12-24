package com.example.resturantmanagmentapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "menu_items")
public class MenuItem {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("MenuItemID")
    private int id;

    @SerializedName("Name")
    private String name;

    @SerializedName("Price")
    private double price;

    @SerializedName("Description")
    private String description;

    @SerializedName("Category")
    private String category;

    @SerializedName("ImageUrl")
    private String imageUrl;

    public MenuItem() {}


    public MenuItem(String name, String description, double price, String category, String imageUrl) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
        this.imageUrl = imageUrl;
    }



    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }



    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}