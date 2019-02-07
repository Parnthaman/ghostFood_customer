package com.ghostFood.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by android1 on 5/25/2017.
 */

public interface BranchesAPI {

    @GET("branch")
    Call<ResponseBranches> Get(@Query("latitude") String mLatitude, @Query("longitude") String mLongitude);
    public class ResponseBranches{

        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("data")
        @Expose
        private List<Datum> data = null;
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

        public List<Datum> getData() {
            return data;
        }

        public void setData(List<Datum> data) {
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


    public class Datum {

        @SerializedName("branch_key")
        @Expose
        private String branch_key;
        @SerializedName("latitude")
        @Expose
        private String latitude;
        @SerializedName("longitude")
        @Expose
        private String longitude;
        @SerializedName("contact_email")
        @Expose
        private String contact_email;
        @SerializedName("contact_number1")
        @Expose
        private Object contact_number1;
        @SerializedName("contact_number2")
        @Expose
        private Object contact_number2;
        @SerializedName("currency_id")
        @Expose
        private Object currency_id;
        @SerializedName("pickup_time")
        @Expose
        private Object pickup_time;
        @SerializedName("delivery_time")
        @Expose
        private Object delivery_time;
        @SerializedName("branch_status")
        @Expose
        private Integer branch_status;
        @SerializedName("approve_status")
        @Expose
        private Integer approve_status;
        @SerializedName("branch_name")
        @Expose
        private String branch_name;
        @SerializedName("branch_address_line1")
        @Expose
        private String branch_address_line1;
        @SerializedName("branch_address_line2")
        @Expose
        private String branch_address_line2;
        @SerializedName("area")
        @Expose
        private String area;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("distance")
        @Expose
        private String distance;

        public String getBranch_key() {
            return branch_key;
        }

        public void setBranch_key(String branch_key) {
            this.branch_key = branch_key;
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

        public String getContact_email() {
            return contact_email;
        }

        public void setContact_email(String contact_email) {
            this.contact_email = contact_email;
        }

        public Object getContact_number1() {
            return contact_number1;
        }

        public void setContact_number1(Object contact_number1) {
            this.contact_number1 = contact_number1;
        }

        public Object getContact_number2() {
            return contact_number2;
        }

        public void setContact_number2(Object contact_number2) {
            this.contact_number2 = contact_number2;
        }

        public Object getCurrency_id() {
            return currency_id;
        }

        public void setCurrency_id(Object currency_id) {
            this.currency_id = currency_id;
        }

        public Object getPickup_time() {
            return pickup_time;
        }

        public void setPickup_time(Object pickup_time) {
            this.pickup_time = pickup_time;
        }

        public Object getDelivery_time() {
            return delivery_time;
        }

        public void setDelivery_time(Object delivery_time) {
            this.delivery_time = delivery_time;
        }

        public Integer getBranch_status() {
            return branch_status;
        }

        public void setBranch_status(Integer branch_status) {
            this.branch_status = branch_status;
        }

        public Integer getApprove_status() {
            return approve_status;
        }

        public void setApprove_status(Integer approve_status) {
            this.approve_status = approve_status;
        }

        public String getBranch_name() {
            return branch_name;
        }

        public void setBranch_name(String branch_name) {
            this.branch_name = branch_name;
        }

        public String getBranch_address_line1() {
            return branch_address_line1;
        }

        public void setBranch_address_line1(String branch_address_line1) {
            this.branch_address_line1 = branch_address_line1;
        }

        public String getBranch_address_line2() {
            return branch_address_line2;
        }

        public void setBranch_address_line2(String branch_address_line2) {
            this.branch_address_line2 = branch_address_line2;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }
    }


}
