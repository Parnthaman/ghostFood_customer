package com.ghostFood.model;

import com.google.android.gms.maps.model.Marker;

public class MarkerList {
    String deliveryboy_key;
    String delivery_boy_name;
    String mobile_number;
    String delivery_boy_email;

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    Marker marker;

    public String getDeliveryboy_key() {
        return deliveryboy_key;
    }

    public void setDeliveryboy_key(String deliveryboy_key) {
        this.deliveryboy_key = deliveryboy_key;
    }

    public String getDelivery_boy_name() {
        return delivery_boy_name;
    }

    public void setDelivery_boy_name(String delivery_boy_name) {
        this.delivery_boy_name = delivery_boy_name;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getDelivery_boy_email() {
        return delivery_boy_email;
    }

    public void setDelivery_boy_email(String delivery_boy_email) {
        this.delivery_boy_email = delivery_boy_email;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    String latitude;
    String longitude;





}