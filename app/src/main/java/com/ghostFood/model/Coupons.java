package com.ghostFood.model;

import com.ghostFood.api.CouponsAPI;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Tech on 6/18/2017.
 */

public class Coupons implements Serializable{
    private String couponKey;
    private Integer couponType;
    private String couponImage;
    private String couponName;
    private String couponDescription;
    private Double couponPrice;
    private String couponCode;
    private Integer userScope;
    private String couponStartDate;
    private String couponEndDate;

    private List<CouponsAPI.Branch> branches;

    public String getCouponKey() {
        return couponKey;
    }

    public void setCouponKey(String couponKey) {
        this.couponKey = couponKey;
    }

    public Integer getCouponType() {
        return couponType;
    }

    public void setCouponType(Integer couponType) {
        this.couponType = couponType;
    }

    public String getCouponImage() {
        return couponImage;
    }

    public void setCouponImage(String couponImage) {
        this.couponImage = couponImage;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getCouponDescription() {
        return couponDescription;
    }

    public void setCouponDescription(String couponDescription) {
        this.couponDescription = couponDescription;
    }

    public Double getCouponPrice() {
        return couponPrice;
    }

    public void setCouponPrice(Double couponPrice) {
        this.couponPrice = couponPrice;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public List<CouponsAPI.Branch> getBranches() {
        return branches;
    }

    public void setBranches(List<CouponsAPI.Branch> branches) {
        this.branches = branches;
    }

    public Integer getUserScope() {
        return userScope;
    }

    public void setUserScope(Integer userScope) {
        this.userScope = userScope;
    }

    public String getCouponStartDate() {
        return couponStartDate;
    }

    public void setCouponStartDate(String couponStartDate) {
        this.couponStartDate = couponStartDate;
    }

    public String getCouponEndDate() {
        return couponEndDate;
    }

    public void setCouponEndDate(String couponEndDate) {
        this.couponEndDate = couponEndDate;
    }
}
