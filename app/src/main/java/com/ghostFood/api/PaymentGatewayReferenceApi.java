package com.ghostFood.api;

import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by developer on 12/8/16.
 */
public interface PaymentGatewayReferenceApi {
    @FormUrlEncoded
    @POST("transaction/recharge")
    Call<ResponsePaymentGatewayReference> getToken(@Field("customer_key") String mCustomerKey,
                                                   @Field("amount") String mAmount,
                                                   @Field("commission") String mCommission);

    public class ResponsePaymentGatewayReference {

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

        @SerializedName("ref_number")
        private String refnumber;

        public String getRefNumber() {
            return refnumber;
        }

        public void setRefNumber(String refnumber) {
            this.refnumber = refnumber;
        }

    }
}
