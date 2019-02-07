package com.ghostFood.api;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ResentOtpApi {
    @FormUrlEncoded
    @POST("user/resend-otp")
    Call<ResentOtpApiResponse> getOtp(@Field("mobile_number") String mobile);

    public class ResentOtpApiResponse {

        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("data")
        @Expose
        private Data data;
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

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public class Data {

            @SerializedName("otp_sent")
            @Expose
            private Integer otpSent;

            public Integer getOtpSent() {
                return otpSent;
            }

            public void setOtpSent(Integer otpSent) {
                this.otpSent = otpSent;
            }

        }

    }

}