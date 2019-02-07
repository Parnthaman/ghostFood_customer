package com.ghostFood.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by android1 on 5/25/2017.
 */

public interface CouponsAPI {

    @GET("coupons")
    Call<ResponseCoupons> Get(@Query("user_key") String mUserKey);

    public class ResponseCoupons {

        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("data")
        @Expose
        private List<Data> data = null;
        @SerializedName("time")
        @Expose
        private Integer time;
        @SerializedName("message")
        @Expose
        private String message;

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public List<Data> getData() {
            return data;
        }

        public void setData(List<Data> data) {
            this.data = data;
        }

        public Integer getTime() {
            return time;
        }

        public void setTime(Integer time) {
            this.time = time;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }

    public class Data {

        @SerializedName("coupon_key")
        @Expose
        private String couponKey;
        @SerializedName("coupon_type")
        @Expose
        private Integer couponType;
        @SerializedName("coupon_value")
        @Expose
        private Double couponValue;
        @SerializedName("coupon_code")
        @Expose
        private String couponCode;
        @SerializedName("coupon_starts_at")
        @Expose
        private String couponStartsAt;
        @SerializedName("coupon_ends_at")
        @Expose
        private String couponEndsAt;
        @SerializedName("coupon_status")
        @Expose
        private Integer couponStatus;
        @SerializedName("coupon_scope")
        @Expose
        private Integer couponScope;
        @SerializedName("coupon_name")
        @Expose
        private String couponName;
        @SerializedName("coupon_description")
        @Expose
        private String couponDescription;
        @SerializedName("coupon_image")
        @Expose
        private String couponImage;
        @SerializedName("branches")
        @Expose
        private List<Branch> branches = null;

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

        public Double getCouponValue() {
            return couponValue;
        }

        public void setCouponValue(Double couponValue) {
            this.couponValue = couponValue;
        }

        public String getCouponCode() {
            return couponCode;
        }

        public void setCouponCode(String couponCode) {
            this.couponCode = couponCode;
        }

        public String getCouponStartsAt() {
            return couponStartsAt;
        }

        public void setCouponStartsAt(String couponStartsAt) {
            this.couponStartsAt = couponStartsAt;
        }

        public String getCouponEndsAt() {
            return couponEndsAt;
        }

        public void setCouponEndsAt(String couponEndsAt) {
            this.couponEndsAt = couponEndsAt;
        }

        public Integer getCouponStatus() {
            return couponStatus;
        }

        public void setCouponStatus(Integer couponStatus) {
            this.couponStatus = couponStatus;
        }

        public Integer getCouponScope() {
            return couponScope;
        }

        public void setCouponScope(Integer couponScope) {
            this.couponScope = couponScope;
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

        public String getCouponImage() {
            return couponImage;
        }

        public void setCouponImage(String couponImage) {
            this.couponImage = couponImage;
        }

        public List<Branch> getBranches() {
            return branches;
        }

        public void setBranches(List<Branch> branches) {
            this.branches = branches;
        }
    }

    public class Branch implements Serializable {

        @SerializedName("branch_key")
        private String branchKey;

        @SerializedName("branch_name")
        private String branchName;

        public String getBranchKey() {
            return branchKey;
        }

        public void setBranchKey(String branchKey) {
            this.branchKey = branchKey;
        }

        public String getBranchName() {
            return branchName;
        }

        public void setBranchName(String branchName) {
            this.branchName = branchName;
        }

    }
}
