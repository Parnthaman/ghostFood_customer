package com.ghostFood.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by android1 on 5/27/2017.
 */

public interface CommissionAPI {
    @GET("commission")
    Call<ResponseCommission> Get(@Query("transaction_type") String mTransactionType
    );

    public class ResponseCommission {

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

    public class BankCommission {

        @SerializedName("fixed_value")
        private Double fixedValue;
        @SerializedName("percentage_value")
        private Double percentageValue;

        public Double getFixedValue() {
            return fixedValue;
        }

        public void setFixedValue(Double fixedValue) {
            this.fixedValue = fixedValue;
        }

        public Double getPercentageValue() {
            return percentageValue;
        }

        public void setPercentageValue(Double percentageValue) {
            this.percentageValue = percentageValue;
        }

    }

    public class Saddid {

        @SerializedName("status")
        private Integer status;
        @SerializedName("type")
        private Integer type;
        @SerializedName("value")
        private Double value;
        @SerializedName("slab")
        private List<Slab> slab = null;

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public Double getValue() {
            return value;
        }

        public void setValue(Double value) {
            this.value = value;
        }

        public List<Slab> getSlab() {
            return slab;
        }

        public void setSlab(List<Slab> slab) {
            this.slab = slab;
        }

    }

    public class Data {

        @SerializedName("bank_commission")
        private BankCommission bankCommission;

        @SerializedName("saddid")
        private Saddid saddid;

        public BankCommission getBankCommission() {
            return bankCommission;
        }

        public void setBankCommission(BankCommission bankCommission) {
            this.bankCommission = bankCommission;
        }

        public Saddid getSaddid() {
            return saddid;
        }

        public void setSaddid(Saddid saddid) {
            this.saddid = saddid;
        }

    }

    public class Slab {

        @SerializedName("amount_upto")
        private Double amountUpto;

        @SerializedName("type")
        private Integer type;

        @SerializedName("value")
        private Double value;

        public Double getAmountUpto() {
            return amountUpto;
        }

        public void setAmountUpto(Double amountUpto) {
            this.amountUpto = amountUpto;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public Double getValue() {
            return value;
        }

        public void setValue(Double value) {
            this.value = value;
        }

    }
}
