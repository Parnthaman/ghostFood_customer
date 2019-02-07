package com.ghostFood.db;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by android1 on 7/3/2017.
 */

public class SpecialItems extends RealmObject {

    @PrimaryKey
    private String specialOfferKey;
    private Integer offerType;
    private String specialOfferName;
    private Integer quantity;
    private Double price;
    private String specialOfferImagePath;
    private Integer userScope;

    public SpecialItems() {

    }

    public SpecialItems(String specialOfferKey, Integer offerType, String specialOfferName, Integer quantity, Double price, String specialOfferImagePath, Integer userScope) {
        this.specialOfferKey = specialOfferKey;
        this.offerType = offerType;
        this.specialOfferName = specialOfferName;
        this.quantity = quantity;
        this.price = price;
        this.specialOfferImagePath = specialOfferImagePath;
        this.userScope = userScope;
    }

    public String getSpecialOfferKey() {
        return specialOfferKey;
    }

    public void setSpecialOfferKey(String specialOfferKey) {
        this.specialOfferKey = specialOfferKey;
    }

    public Integer getOfferType() {
        return offerType;
    }

    public void setOfferType(Integer offerType) {
        this.offerType = offerType;
    }

    public String getSpecialOfferName() {
        return specialOfferName;
    }

    public void setSpecialOfferName(String specialOfferName) {
        this.specialOfferName = specialOfferName;
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

    public String getSpecialOfferImagePath() {
        return specialOfferImagePath;
    }

    public void setSpecialOfferImagePath(String specialOfferImagePath) {
        this.specialOfferImagePath = specialOfferImagePath;
    }

    public Integer getUserScope() {
        return userScope;
    }

    public void setUserScope(Integer userScope) {
        this.userScope = userScope;
    }
}
