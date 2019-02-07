package com.ghostFood.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by android1 on 5/25/2017.
 */

public interface UserAddressListAPI {

    @GET("user-address")
    Call<ResponseAddress> Get(@Query("user_key") String mUserKey);
    public class ResponseAddress {

        @SerializedName("status")
        private Integer status;

        @SerializedName("data")
        private List<Data> data = null;

        @SerializedName("time")
        private Integer time;

        @SerializedName("message")
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

        @SerializedName("latitude")
        private Double latitude;

        @SerializedName("longitude")
        private Double longitude;

        @SerializedName("user_address_key")
        private String addressKey;

        @SerializedName("address_contact_name")
        private String addressContactName;

        @SerializedName("building_name")
        private String buildingName;

        @SerializedName("flat_no")
        private String flatNo;

        @SerializedName("address_line1")
        private String addressLine1;

        @SerializedName("address_line2")
        private String addressLine2;

        @SerializedName("address_area")
        private String addressArea;

        @SerializedName("address_city")
        private String addressCity;

        @SerializedName("address_country")
        private String addressCountry;

        @SerializedName("address_zip_code")
        private String addressZipCode;

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

        public String getAddressKey() {
            return addressKey;
        }

        public void setAddressKey(String addressKey) {
            this.addressKey = addressKey;
        }

        public String getAddressContactName() {
            return addressContactName;
        }

        public void setAddressContactName(String addressContactName) {
            this.addressContactName = addressContactName;
        }

        public String getBuildingName() {
            return buildingName;
        }

        public void setBuildingName(String buildingName) {
            this.buildingName = buildingName;
        }

        public String getFlatNo() {
            return flatNo;
        }

        public void setFlatNo(String flatNo) {
            this.flatNo = flatNo;
        }

        public String getAddressLine1() {
            return addressLine1;
        }

        public void setAddressLine1(String addressLine1) {
            this.addressLine1 = addressLine1;
        }

        public String getAddressLine2() {
            return addressLine2;
        }

        public void setAddressLine2(String addressLine2) {
            this.addressLine2 = addressLine2;
        }

        public String getAddressArea() {
            return addressArea;
        }

        public void setAddressArea(String addressArea) {
            this.addressArea = addressArea;
        }

        public String getAddressCity() {
            return addressCity;
        }

        public void setAddressCity(String addressCity) {
            this.addressCity = addressCity;
        }

        public String getAddressCountry() {
            return addressCountry;
        }

        public void setAddressCountry(String addressCountry) {
            this.addressCountry = addressCountry;
        }

        public String getAddressZipCode() {
            return addressZipCode;
        }

        public void setAddressZipCode(String addressZipCode) {
            this.addressZipCode = addressZipCode;
        }
    }
}
