package com.ghostFood.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by android1 on 6/8/2017.
 */

public interface PaymentResponseAPI {
    @GET("payment/status/{OrderKey}")
    Call<paymentResponse> Get(@Path("OrderKey") String mOrderKey);

    public class paymentResponse {

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


        }


    }
}
