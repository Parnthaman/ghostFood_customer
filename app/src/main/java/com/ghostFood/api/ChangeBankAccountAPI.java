package com.ghostFood.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.PUT;

/**
 * Created by android1 on 5/25/2017.
 */

public interface ChangeBankAccountAPI {

    @FormUrlEncoded
    @PUT("profile/change-bank-account")
    Call<ResponseChangeBankAccount> Change(@Field("customer_key") String mUserKey,
                                           @Field("beneficiary_name") String mBeneficiaryName,
                                           @Field("bank_id") String mBankId,
                                           @Field("iban") String mIBAN,
                                           @Field("beneficiary_address") String mBeneficiaryAddress,
                                           @Field("customer_bank_key") String mCustomerBankKey
    );
    public class ResponseChangeBankAccount {
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

    }
}
