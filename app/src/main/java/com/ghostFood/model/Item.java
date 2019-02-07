package com.ghostFood.model;

import java.io.Serializable;

/**
 * Created by Tech on 6/16/2017.
 */

public class Item implements Serializable {

    private String itemKey;
    private String itemName;
    private String itemImage;
    private Double itemPrice;
    private Double totalPrice;
    private Integer quantity;
    private String ingredientsName;
    private Integer food_type;

    public String getItemKey() {
        return itemKey;
    }

    public void setItemKey(String itemKey) {
        this.itemKey = itemKey;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public Double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getIngredientsName() {
        return ingredientsName;
    }

    public void setIngredientsName(String ingredientsName) {
        this.ingredientsName = ingredientsName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getFood_type() {
        return food_type;
    }

    public void setFood_type(Integer food_type) {
        this.food_type = food_type;
    }
}
