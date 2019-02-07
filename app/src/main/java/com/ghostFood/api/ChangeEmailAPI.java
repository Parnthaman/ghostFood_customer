package com.ghostFood.api;

import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.PUT;

/**
 * Created by android1 on 5/27/2017.
 */

public interface ChangeEmailAPI {
    @FormUrlEncoded
    @PUT("profile/change-email")
    Call<ResponseChangeEmail> Update(@Field("customer_key") String mCustomerKey,
                                     @Field("email_address") String mEmailAddress);

    public class ResponseChangeEmail {
        @SerializedName("status")
        private Integer status;

        @SerializedName("data")
        private Data data;

        @SerializedName("time")
        private Integer time;

        @SerializedName("response_code")
        private Integer responseCode;

        @SerializedName("message")
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


    }
}
