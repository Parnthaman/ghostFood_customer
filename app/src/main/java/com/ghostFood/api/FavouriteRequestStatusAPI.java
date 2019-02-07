package com.ghostFood.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.PUT;

/**
 * Created by android1 on 5/25/2017.
 */

public interface FavouriteRequestStatusAPI {
    @FormUrlEncoded
    @PUT("profile/favourite-request-status")
    Call<ResponseFavouriteRequestStatus> Update(
            @Field("from_customer_key") String mFromUserKey,
            @Field("to_customer_key") String mToUserKey,
            @Field("favourite_status") Integer mRequestStatus
    );

    public class ResponseFavouriteRequestStatus {
        private Integer status;
        private Data data = null;
        private Integer time;
        private Integer responseCode;
        private String message;

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

        public Integer getTime() {
            return time;
        }

        public void setTime(Integer time) {
            this.time = time;
        }

        public Integer getResponseCode() {
            return responseCode;
        }

        public void setResponseCode(Integer responseCode) {
            this.responseCode = responseCode;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public class Data {
        private String name;
        private String number_orignal;
        private String number;
        private String contact_customer_key;
        private String user_name;
        private Integer saddid_user;
        private Integer request_method;
        private Integer request_status;
        private Integer favourite_status;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNumber_orignal() {
            return number_orignal;
        }

        public void setNumber_orignal(String number_orignal) {
            this.number_orignal = number_orignal;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getContact_customer_key() {
            return contact_customer_key;
        }

        public void setContact_customer_key(String contact_customer_key) {
            this.contact_customer_key = contact_customer_key;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public Integer getSaddid_user() {
            return saddid_user;
        }

        public void setSaddid_user(Integer saddid_user) {
            this.saddid_user = saddid_user;
        }

        public Integer getRequest_method() {
            return request_method;
        }

        public void setRequest_method(Integer request_method) {
            this.request_method = request_method;
        }

        public Integer getRequest_status() {
            return request_status;
        }

        public void setRequest_status(Integer request_status) {
            this.request_status = request_status;
        }

        public Integer getFavourite_status() {
            return favourite_status;
        }

        public void setFavourite_status(Integer favourite_status) {
            this.favourite_status = favourite_status;
        }
    }
}
