package com.ghostFood.api;

import com.google.gson.annotations.SerializedName;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by android1 on 5/25/2017.
 */

public interface RegistraionCorporateApi {
    @Multipart
    @POST("customer/register-corporate")
    Call<ResponseRegistraionCorporate> Register(@Part("user_name") RequestBody mUserName,
                                                @Part("customer_name") RequestBody mCustomerName,
                                                @Part("country_code") RequestBody mCountryCode,
                                                @Part("mobile_number") RequestBody mMobileNumber,
                                                @Part("email_address") RequestBody mEmailAddress,
                                                @Part("password") RequestBody mPassword,
                                                @Part("confirm_password") RequestBody mConfirmPassword,
                                                @Part("civil_id") RequestBody mCivilId,
                                                @Part("beneficiary_name") RequestBody mBeneficiaryName,
                                                @Part("bank_id") RequestBody mBankId,
                                                @Part("iban") RequestBody mIBAN,
                                                @Part("beneficiary_address") RequestBody mBeneficiaryAddress,
                                                @Part("device_token") RequestBody mDeviceToken,
                                                @Part("device_type_id") RequestBody mDeviceTypeId,
                                                @Part MultipartBody.Part mDocCivilD,
                                                @Part MultipartBody.Part mDocCommercialLicence,
                                                @Part MultipartBody.Part mDocProofOfSignature);

    public class ResponseRegistraionCorporate {
        @SerializedName("status")
        private Integer status;

        @SerializedName("data")
        private Data data;

        @SerializedName("time")
        private Integer time;

        @SerializedName("response_code")
        private Integer responseCode;

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
