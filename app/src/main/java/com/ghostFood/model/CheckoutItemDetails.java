package com.ghostFood.model;

import java.util.ArrayList;

/**
 * Created by android1 on 7/5/2017.
 */

public class CheckoutItemDetails {
    private String item_key;
    private Double item_price;
    private int quantity;
    private ArrayList<CheckoutExtras> modifiers;
    private ArrayList<CheckoutExtras> ingrdients;
    private ArrayList<CheckoutExtras> specialOffers;

    public String getItem_key() {
        return item_key;
    }

    public void setItem_key(String item_key) {
        this.item_key = item_key;
    }


    public Double getItem_price() {
        return item_price;
    }

    public void setItem_price(Double item_price) {
        this.item_price = item_price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ArrayList<CheckoutExtras> getModifiers() {
        return modifiers;
    }

    public void setModifiers(ArrayList<CheckoutExtras> modifiers) {
        this.modifiers = modifiers;
    }

    public ArrayList<CheckoutExtras> getIngrdients() {
        return ingrdients;
    }

    public void setIngrdients(ArrayList<CheckoutExtras> ingrdients) {
        this.ingrdients = ingrdients;
    }

    public ArrayList<CheckoutExtras> getSpecialOffers() {
        return specialOffers;
    }

    public void setSpecialOffers(ArrayList<CheckoutExtras> specialOffers) {
        this.specialOffers = specialOffers;
    }
}
