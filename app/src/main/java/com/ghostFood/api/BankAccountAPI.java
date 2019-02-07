package com.ghostFood.api;

import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by android1 on 5/25/2017.
 */

public interface BankAccountAPI {

    @GET("profile/bank-account")
    Call<ResponseBankAccount> Get(@Query("customer_key") String mUserKey
    );
    public class ResponseBankAccount {
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

        @SerializedName("civil_id")
        private String civilId;

        @SerializedName("customer_bank_key")
        private String customerBankKey;

        @SerializedName("beneficiary_name")
        private String beneficiaryName;

        @SerializedName("bank_id")
        private String bankId;

        @SerializedName("iban")
        private String iban;

        @SerializedName("beneficiary_address")
        private String beneficiaryAddress;

        @SerializedName("bank_account_status")
        private String bankAccountStatus;

        @SerializedName("bank_name")
        private String bank_name;

        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }

        public String getCivilId() {
            return civilId;
        }

        public void setCivilId(String civilId) {
            this.civilId = civilId;
        }

        public String getCustomerBankKey() {
            return customerBankKey;
        }

        public void setCustomerBankKey(String customerBankKey) {
            this.customerBankKey = customerBankKey;
        }

        public String getBeneficiaryName() {
            return beneficiaryName;
        }

        public void setBeneficiaryName(String beneficiaryName) {
            this.beneficiaryName = beneficiaryName;
        }

        public String getBankId() {
            return bankId;
        }

        public void setBankId(String bankId) {
            this.bankId = bankId;
        }

        public String getIban() {
            return iban;
        }

        public void setIban(String iban) {
            this.iban = iban;
        }

        public String getBeneficiaryAddress() {
            return beneficiaryAddress;
        }

        public void setBeneficiaryAddress(String beneficiaryAddress) {
            this.beneficiaryAddress = beneficiaryAddress;
        }

        public String getBankAccountStatus() {
            return bankAccountStatus;
        }

        public void setBankAccountStatus(String bankAccountStatus) {
            this.bankAccountStatus = bankAccountStatus;
        }

    }
}
