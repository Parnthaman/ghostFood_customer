package com.ghostFood.api;

import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by developer on 12/8/16.
 */
public interface PaymentGatewayApi {
    @FormUrlEncoded
    @POST("authpost")
    Call<ResponsePaymentGateway> getToken(@Field("MerchantCode") String mMerchantCode, @Field("Amount") String mAmount,
                                          @Field("SuccessUrl") String mSuccessUrl, @Field("FailureUrl") String mFailureUrl,
                                          @Field("Variable1") String mVariable1,
                                          @Field("Variable2") String mVariable2,
                                          @Field("Variable3") String mVariable3,
                                          @Field("Variable4") String mVariable4,
                                          @Field("Variable5") String mVariable5);

    public class ResponsePaymentGateway {

        @SerializedName("status")
        private String status;
        @SerializedName("message")
        private String message;
        @SerializedName("data")
        private Data data;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

    }

    public class Data {

        @SerializedName("token")
        private Integer token;
        @SerializedName("paymenturl")
        private String paymenturl;

        public Integer getToken() {
            return token;
        }

        public void setToken(Integer token) {
            this.token = token;
        }

        public String getPaymenturl() {
            return paymenturl;
        }

        public void setPaymenturl(String paymenturl) {
            this.paymenturl = paymenturl;
        }

    }
}
