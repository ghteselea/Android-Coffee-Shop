package com.example.android.justjava.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "orders")
public class OrderEntity {

    @PrimaryKey
    private Long id;
    private String foodName;
    @ColumnInfo(name = "type") private String foodType; // food_type by default
    private int quantity;
    private int price;
    private int imageId;

    public OrderEntity() {

    }

    public OrderEntity(String foodName, String foodType, int quantity, int price, int image) {
        this.foodName = foodName;
        this.foodType = foodType;
        this.quantity = quantity;
        this.price = price;
        imageId = image;
    }

    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
