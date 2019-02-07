package com.ghostFood.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by android1 on 5/25/2017.
 */

public interface RegistraionUserApi {
    @FormUrlEncoded
    @POST("user/register")
    Call<ResponseRegistraionUser> Register(
            @Field("registration_type") Integer mRegistrationType,
            @Field("username") String mUserName,
            @Field("first_name") String mFirstName,
            @Field("last_name") String mLastName,
            @Field("email") String mEmailAddress,
            @Field("password") String mPassword,
            @Field("confirm_password") String mConfirmPassword,
            @Field("mobile_number") String mMobileNumber,

            @Field("extra_variable1") String mExtraVariable1,
            @Field("extra_variable2") String mExtraVariable2,
            @Field("extra_variable3") String mExtraVariable3,
            @Field("extra_variable4") String mExtraVariable4,
            @Field("extra_variable5") String mExtraVariable5,

            @Field("device_token") String mDeviceToken,
            @Field("device_type_id") String mDeviceTypeId);

    public class ResponseRegistraionUser {
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("data")
        @Expose
        private List<Data> data = null;
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
    }

    class Data {

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
