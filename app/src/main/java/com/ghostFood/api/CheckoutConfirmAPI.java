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

public interface CheckoutConfirmAPI {

    @POST("order/confirm")
    Call<ResponseCheckoutConfirm> Confirm(@Body RequestBody mRawData);

    public class ResponseCheckoutConfirm implements Serializable {
        private Integer status;
        private Data data;
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

    public class Data implements Serializable {

        @SerializedName("order_number")
        private String orderNumber;
        @SerializedName("order_key")
        private String order_key;

        @SerializedName("loyalty_points")
        private String loyaltyPoints;

        @SerializedName("branch")
        private List<Branch> branch = null;

        @SerializedName("time_slot")
        private List<String> timeSlot = null;

        @SerializedName("payment_details")
        private List<PaymentDetail> paymentDetails = null;

        @SerializedName("delivery_option")
        private Integer deliveryOption;

        @SerializedName("payment_option")
        private Integer paymentOption;

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public String getLoyaltyPoints() {
            return loyaltyPoints;
        }

        public void setLoyaltyPoints(String loyaltyPoints) {
            this.loyaltyPoints = loyaltyPoints;
        }

        public List<Branch> getBranch() {
            return branch;
        }

        public void setBranch(List<Branch> branch) {
            this.branch = branch;
        }

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

        public String getOrder_key() {
            return order_key;
        }

        public void setOrder_key(String order_key) {
            this.order_key = order_key;
        }
    }

    public class PaymentDetail implements Serializable {

        @SerializedName("name")
        private String name;

        @SerializedName("value")
        private Integer value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

    }

    public class Branch implements Serializable {

        @SerializedName("branch_key")
        private String branchKey;

        @SerializedName("contact_email")
        private String contactEmail;

        @SerializedName("contact_number1")
        private Object contactNumber1;

        @SerializedName("contact_number2")
        private Object contactNumber2;

        @SerializedName("branch_name")
        private String branchName;

        @SerializedName("branch_address_line1")
        private String branchAddressLine1;

        @SerializedName("branch_address_line2")
        private String branchAddressLine2;

        @SerializedName("area")
        private String area;

        @SerializedName("city")
        private String city;

        @SerializedName("country")
        private String country;

        public String getBranchKey() {
            return branchKey;
        }

        public void setBranchKey(String branchKey) {
            this.branchKey = branchKey;
        }

        public String getContactEmail() {
            return contactEmail;
        }

        public void setContactEmail(String contactEmail) {
            this.contactEmail = contactEmail;
        }

        public Object getContactNumber1() {
            return contactNumber1;
        }

        public void setContactNumber1(Object contactNumber1) {
            this.contactNumber1 = contactNumber1;
        }

        public Object getContactNumber2() {
            return contactNumber2;
        }

        public void setContactNumber2(Object contactNumber2) {
            this.contactNumber2 = contactNumber2;
        }

        public String getBranchName() {
            return branchName;
        }

        public void setBranchName(String branchName) {
            this.branchName = branchName;
        }

        public String getBranchAddressLine1() {
            return branchAddressLine1;
        }

        public void setBranchAddressLine1(String branchAddressLine1) {
            this.branchAddressLine1 = branchAddressLine1;
        }

        public String getBranchAddressLine2() {
            return branchAddressLine2;
        }

        public void setBranchAddressLine2(String branchAddressLine2) {
            this.branchAddressLine2 = branchAddressLine2;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

    }
}
