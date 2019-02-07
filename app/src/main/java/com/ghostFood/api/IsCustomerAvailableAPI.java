package com.ghostFood.api;

import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by android1 on 5/27/2017.
 */

public interface IsCustomerAvailableAPI {
    @GET("customer/is-customer-available")
    Call<ResponseIsCustomerAvailable> Get(@Query("identity") String mIdentity,
                                          @Query("customer_key") String mCustomerKey
    );

    public class ResponseIsCustomerAvailable {

        private Integer status;
        private Data data;
        private Integer time;
        private Integer response_code;
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

        public Integer getResponse_code() {
            return response_code;
        }

        public void setResponse_code(Integer response_code) {
            this.response_code = response_code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }

    public class Data {

        @SerializedName("user_name")
        private String userName;

        @SerializedName("email_address")
        private String emailAddress;

        @SerializedName("mobile_number")
        private String mobileNumber;

        @SerializedName("customer_name")
        private String customerName;

        @SerializedName("customer_key")
        private String customerKey;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getEmailAddress() {
            return emailAddress;
        }

        public void setEmailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
        }

        public String getMobileNumber() {
            return mobileNumber;
        }

        public void setMobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getCustomerKey() {
            return customerKey;
        }

        public void setCustomerKey(String customerKey) {
            this.customerKey = customerKey;
        }

    }
}
