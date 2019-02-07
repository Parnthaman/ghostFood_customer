package com.ghostFood.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by android1 on 5/25/2017.
 */

public interface OrdersAPI {

    @GET("order")
    Call<ResponseOrders> Get(@Query("user_key") String mUserKey);
    public class ResponseOrders {

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

        @SerializedName("order_key")
        private String orderKey;

        @SerializedName("order_number")
        private String orderNumber;

        @SerializedName("order_status")
        private Integer orderStatus;

        @SerializedName("order_date_time")
        private String orderDateTime;

        @SerializedName("order_total")
        private Double orderTotal;

        public String getOrderKey() {
            return orderKey;
        }

        public void setOrderKey(String orderKey) {
            this.orderKey = orderKey;
        }

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public Integer getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(Integer orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getOrderDateTime() {
            return orderDateTime;
        }

        public void setOrderDateTime(String orderDateTime) {
            this.orderDateTime = orderDateTime;
        }

        public Double getOrderTotal() {
            return orderTotal;
        }

        public void setOrderTotal(Double orderTotal) {
            this.orderTotal = orderTotal;
        }
    }
}
