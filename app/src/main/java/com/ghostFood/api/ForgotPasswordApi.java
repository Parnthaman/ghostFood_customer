package com.ghostFood.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.PUT;

/**
 * Created by developer on 12/8/16.
 */
public interface ForgotPasswordApi {
    @FormUrlEncoded
    @PUT("user/forgot-password")
    Call<ResponseForgotPassword> Get(@Field("email_address") String mEmailAddress);

    public class ResponseForgotPassword {

        private Integer status;
        private List<Data> data;
        private Integer time;
        private Integer responseCode;
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
