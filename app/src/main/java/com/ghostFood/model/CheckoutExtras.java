package com.ghostFood.model;

/**
 * Created by android1 on 7/5/2017.
 */

public class CheckoutExtras {
    private String key;
    private String group_key;
    private Double price;
    private Boolean is_selected_drink;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getGroup_key() {
        return group_key;
    }

    public void setGroup_key(String group_key) {
        this.group_key = group_key;
    }

    public Boolean getIs_selected_drink() {
        return is_selected_drink;
    }

    public void setIs_selected_drink(Boolean is_selected_drink) {
        this.is_selected_drink = is_selected_drink;
    }
}
