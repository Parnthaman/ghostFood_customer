package com.ghostFood.api;

import com.google.gson.annotations.SerializedName;

import java.math.BigInteger;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by developer on 12/8/16.
 */
public interface LoginApi {
    @FormUrlEncoded
    @POST("user/login")
    Call<ResponseLogin> Login(@Field("username") String mUserName, @Field("password") String mPassword,
                              @Field("device_token") String mDeviceToken, @Field("device_type_id") String mDeviceTypeId);

    public class ResponseLogin {

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

    public class Data {

        @SerializedName("user_key")
        private String userKey;

        @SerializedName("first_name")
        private String firstName;

        @SerializedName("last_name")
        private String lastName;

        @SerializedName("username")
        private String username;

        @SerializedName("country_code")
        private String countryCode;

        @SerializedName("mobile_number")
        private BigInteger mobileNumber;

        @SerializedName("email")
        private String emailAddress;

        @SerializedName("default_payment_receive_in")
        private Integer defaultPaymentReceiveIn;

        @SerializedName("wallet_balance")
        private String walletBalance;

        @SerializedName("profile_image")
        private String profileImage;

        @SerializedName("password_new_status")
        private Integer passwordNewStatus;

        @SerializedName("customer_status")
        private Integer customerStatus;

        @SerializedName("access_token")
        private String accessToken;


        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        public BigInteger getMobileNumber() {
            return mobileNumber;
        }

        public void setMobileNumber(BigInteger mobileNumber) {
            this.mobileNumber = mobileNumber;
        }

        public String getEmailAddress() {
            return emailAddress;
        }

        public void setEmailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
        }

        public Integer getDefaultPaymentReceiveIn() {
            return defaultPaymentReceiveIn;
        }

        public void setDefaultPaymentReceiveIn(Integer defaultPaymentReceiveIn) {
            this.defaultPaymentReceiveIn = defaultPaymentReceiveIn;
        }

        public String getWalletBalance() {
            return walletBalance;
        }

        public void setWalletBalance(String walletBalance) {
            this.walletBalance = walletBalance;
        }

        public String getProfileImage() {
            return profileImage;
        }

        public void setProfileImage(String profileImage) {
            this.profileImage = profileImage;
        }

        public Integer getPasswordNewStatus() {
            return passwordNewStatus;
        }

        public void setPasswordNewStatus(Integer passwordNewStatus) {
            this.passwordNewStatus = passwordNewStatus;
        }

        public Integer getCustomerStatus() {
            return customerStatus;
        }

        public void setCustomerStatus(Integer customerStatus) {
            this.customerStatus = customerStatus;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getUserKey() {
            return userKey;
        }

        public void setUserKey(String userKey) {
            this.userKey = userKey;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
    }
}
