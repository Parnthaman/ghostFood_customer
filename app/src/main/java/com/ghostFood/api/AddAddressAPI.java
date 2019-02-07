package com.ghostFood.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by android1 on 5/25/2017.
 */

public interface AddAddressAPI {

    @FormUrlEncoded
    @POST("user-address/create")
    Call<ResponseAddress> Create(
            @Field("user_key") String mUserKey,
            @Field("address_contact_name") String mAddressContactName,
            @Field("building_name") String mBuildingName,
            @Field("flat_no") String mFlatRoomNo,
            @Field("address_line1") String mAddressLine1,
            @Field("address_line2") String mAddressLine2,
            @Field("area") String mArea,
            @Field("city") String mCity,
            @Field("country") String mCountry,
            @Field("zip_code") String mZipCode,
            @Field("latitude") Double mLatitude,
            @Field("longitude") Double mLongitude
    );

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
        @SerializedName("address_key")
        private String addressKey;

        @SerializedName("address_contact_name")
        private String addressContactName;

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
