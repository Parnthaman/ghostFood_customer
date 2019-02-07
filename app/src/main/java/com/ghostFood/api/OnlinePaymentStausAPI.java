package com.ghostFood.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by android1 on 5/04/2018.
 */

public interface OnlinePaymentStausAPI {

    @GET("payment/status/{ORDER_KEY}")
    Call<OnlinePaymentStaus> Get(@Path("ORDER_KEY") String mOrderKey);

    public class OnlinePaymentStaus {

        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("data")
        @Expose
        private Data data;
        @SerializedName("time")
        @Expose
        private Long time;
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

        public Long getTime() {
            return time;
        }

        public void setTime(Long time) {
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

        @SerializedName("loyalty_points")
        @Expose
        private String loyalty_points;
        @SerializedName("order_number")
        @Expose
        private String order_number;
        @SerializedName("user_id")
        @Expose
        private Integer user_id;
        @SerializedName("order_key")
        @Expose
        private String order_key;

        public String getLoyalty_points() {
            return loyalty_points;
        }

        public void setLoyalty_points(String loyalty_points) {
            this.loyalty_points = loyalty_points;
        }

        public String getOrder_number() {
            return order_number;
        }

        public void setOrder_number(String order_number) {
            this.order_number = order_number;
        }

        public Integer getUser_id() {
            return user_id;
        }

        public void setUser_id(Integer user_id) {
            this.user_id = user_id;
        }

        public String getOrder_key() {
            return order_key;
        }

        public void setOrder_key(String order_key) {
            this.order_key = order_key;
        }

    }
}
