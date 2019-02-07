package com.ghostFood.model;

import com.ghostFood.util.ConstantsInternal;

/**
 * Created by Tech on 6/16/2017.
 */

public class CartItem {

    private Integer sNo;
    private String itemKey;
    private String itemName;
    private String itemImage;
    private String itemPrice;
    private int quantity;
    private String totalPrice;
    private String modifierList, IngrdientsList, SpecialOffers;
    private Integer userScope;
    private ConstantsInternal.ItemType ItemType;

    public Integer getsNo() {
        return sNo;
    }

    public void setsNo(Integer sNo) {
        this.sNo = sNo;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
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

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getModifierList() {
        return modifierList;
    }

    public void setModifierList(String modifierList) {
        this.modifierList = modifierList;
    }

    public String getIngrdientsList() {
        return IngrdientsList;
    }

    public String getSpecialOffers() {
        return SpecialOffers;
    }

    public void setSpecialOffers(String specialOffers) {
        SpecialOffers = specialOffers;
    }

    public Integer getUserScope() {
        return userScope;
    }

    public void setUserScope(Integer userScope) {
        this.userScope = userScope;
    }

    public void setIngrdientsList(String ingrdientsList) {
        IngrdientsList = ingrdientsList;
    }

    public ConstantsInternal.ItemType getItemType() {
        return ItemType;
    }

    public void setItemType(ConstantsInternal.ItemType itemType) {
        ItemType = itemType;
    }
}
