package com.ghostFood.db;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by android1 on 7/3/2017.
 */

public class Items extends RealmObject {

    @PrimaryKey
    private Integer sNo;
    private String itemKey;
    private String itemName;
    private String modifier;
    private String ingredients;
    private String specialOffer;
    private Integer quantity;
    private Double price;
    private String itemImagePath;
    private Integer userScope;

    public Items() {

    }

    public Items(Integer sNo, String itemKey, String itemName, String modifier, String ingredients, String specialOffer, Integer quantity, Double price, String itemImagePath, Integer userScope) {
        this.sNo = sNo;
        this.itemKey = itemKey;
        this.itemName = itemName;
        this.modifier = modifier;
        this.ingredients = ingredients;
        this.specialOffer = specialOffer;
        this.quantity = quantity;
        this.price = price;
        this.itemImagePath = itemImagePath;
        this.userScope = userScope;
    }

    public Integer getsNo() {
        return sNo;
    }

    public void setsNo(Integer sNo) {
        this.sNo = sNo;
    }

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

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getSpecialOffer() {
        return specialOffer;
    }

    public void setSpecialOffer(String specialOffer) {
        this.specialOffer = specialOffer;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getItemImagePath() {
        return itemImagePath;
    }

    public void setItemImagePath(String itemImagePath) {
        this.itemImagePath = itemImagePath;
    }

    public Integer getUserScope() {
        return userScope;
    }

    public void setUserScope(Integer userScope) {
        this.userScope = userScope;
    }
}