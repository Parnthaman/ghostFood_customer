package com.ghostFood.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by android1 on 5/25/2017.
 */

public interface CheckoutAPI {

    @POST("order/verify")
    Call<ResponseCheckout> Verify(@Body RequestBody mRawData);
    public class ResponseCheckout implements Serializable{
        @SerializedName("status")
        private Integer status;

        @SerializedName("data")
        private Data data;

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


    }

    public class Data  implements Serializable{

        @SerializedName("time_slot")
        private List<String> timeSlot = null;

        @SerializedName("payment_details")
        private List<PaymentDetail> paymentDetails = null;

        @SerializedName("delivery_option")
        private Integer deliveryOption;

        @SerializedName("payment_option")
        private Integer paymentOption;

        @SerializedName("loyalty_points")
        private Integer loyaltyPoints;

        @SerializedName("show_loyalty_points")
        private Boolean show_loyalty_points;

        public List<String> getTimeSlot() {
            return timeSlot;
        }

        public void setTimeSlot(List<String> timeSlot) {
            this.timeSlot = timeSlot;
        }

        public List<PaymentDetail> getPaymentDetails() {
            return paymentDetails;
        }

        public void setPaymentDetails(List<PaymentDetail> paymentDetails) {
            this.paymentDetails = paymentDetails;
        }

        public Integer getDeliveryOption() {
            return deliveryOption;
        }

        public void setDeliveryOption(Integer deliveryOption) {
            this.deliveryOption = deliveryOption;
        }

        public Integer getPaymentOption() {
            return paymentOption;
        }

        public void setPaymentOption(Integer paymentOption) {
            this.paymentOption = paymentOption;
        }

        public Integer getLoyaltyPoints() {
            return loyaltyPoints;
        }

        public void setLoyaltyPoints(Integer loyaltyPoints) {
            this.loyaltyPoints = loyaltyPoints;
        }

        public Boolean getShow_loyalty_points() {
            return show_loyalty_points;
        }

        public void setShow_loyalty_points(Boolean show_loyalty_points) {
            this.show_loyalty_points = show_loyalty_points;
        }
    }

    public class PaymentDetail implements Serializable{

        @SerializedName("name")
        private String name;

        @SerializedName("value")
        private Double value;

        @SerializedName("symbol")
        private String symbol;

        @SerializedName("color")
        private Boolean color;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Double getValue() {
            return value;
        }

        public void setValue(Double value) {
            this.value = value;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public Boolean getColor() {
            return color;
        }

        public void setColor(Boolean color) {
            this.color = color;
        }

    }

    public class TimeSlot implements Serializable {

        @SerializedName("branch_timeslot_key")
        private String branchTimeslotKey;
        @SerializedName("start")
        private String start;
        @SerializedName("end")
        private String end;
        @SerializedName("status")
        private String status;

        public String getBranchTimeslotKey() {
            return branchTimeslotKey;
        }

        public void setBranchTimeslotKey(String branchTimeslotKey) {
            this.branchTimeslotKey = branchTimeslotKey;
        }

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }
}
