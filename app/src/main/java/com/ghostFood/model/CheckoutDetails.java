package com.ghostFood.model;

import java.util.ArrayList;

/**
 * Created by android1 on 7/5/2017.
 */

public class CheckoutDetails {

    private Integer order_type;
    private String branch_key;
    private String user_key;
    private String address_key;
    private String mobile_number = "";
    private String email = "";
    private ArrayList<CheckoutItemDetails> items;
    private ArrayList<CheckoutItemDetails> offers;
    private ArrayList<CheckoutItemDetails> special_offers;
    private ArrayList<CheckoutItemDetails> special_items;
    private ArrayList<CheckoutItemDetails> coupon_items;
    private String delivery_time = "";
    private String payment_type = "";
    private Double latitude;
    private Double longitude;
    private Integer payment_option;
    private Integer delivery_option;
    private String coupon_code;
    private Boolean use_loyalty_points = false;

    public Integer getOrder_type() {
        return order_type;
    }

    public void setOrder_type(Integer order_type) {
        this.order_type = order_type;
    }

    public String getBranch_key() {
        return branch_key;
    }

    public void setBranch_key(String branch_key) {
        this.branch_key = branch_key;
    }

    public String getUser_key() {
        return user_key;
    }

    public void setUser_key(String user_key) {
        this.user_key = user_key;
    }

    public String getAddress_key() {
        return address_key;
    }

    public void setAddress_key(String address_key) {
        this.address_key = address_key;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<CheckoutItemDetails> getItems() {
        return items;
    }

    public void setItems(ArrayList<CheckoutItemDetails> items) {
        this.items = items;
    }

    public ArrayList<CheckoutItemDetails> getOffers() {
        return offers;
    }

    public void setOffers(ArrayList<CheckoutItemDetails> offers) {
        this.offers = offers;
    }

    public ArrayList<CheckoutItemDetails> getSpecial_offers() {
        return special_offers;
    }

    public void setSpecial_offers(ArrayList<CheckoutItemDetails> special_offers) {
        this.special_offers = special_offers;
    }

    public ArrayList<CheckoutItemDetails> getSpecial_items() {
        return special_items;
    }

    public void setSpecial_items(ArrayList<CheckoutItemDetails> special_items) {
        this.special_items = special_items;
    }

    public ArrayList<CheckoutItemDetails> getCoupon_items() {
        return coupon_items;
    }

    public void setCoupon_items(ArrayList<CheckoutItemDetails> coupon_items) {
        this.coupon_items = coupon_items;
    }

    public String getDelivery_time() {
        return delivery_time;
    }

    public void setDelivery_time(String delivery_time) {
        this.delivery_time = delivery_time;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getPayment_option() {
        return payment_option;
    }

    public void setPayment_option(Integer payment_option) {
        this.payment_option = payment_option;
    }

    public Integer getDelivery_option() {
        return delivery_option;
    }

    public void setDelivery_option(Integer delivery_option) {
        this.delivery_option = delivery_option;
    }

    public String getCoupon_code() {
        return coupon_code;
    }

    public void setCoupon_code(String coupon_code) {
        this.coupon_code = coupon_code;
    }

    public Boolean getUse_loyalty_points() {
        return use_loyalty_points;
    }

    public void setUse_loyalty_points(Boolean use_loyalty_points) {
        this.use_loyalty_points = use_loyalty_points;
    }
}
