package com.ghostFood.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by android1 on 6/8/2017.
 */

public interface WalletPlanAPI {
    @GET("wallet/plan")
    Call<ResponseWalletPlan> Get();

    public class ResponseWalletPlan {
        @SerializedName("status")
        private Integer status;

        @SerializedName("data")
        private List<Double> data = null;

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

        public List<Double> getData() {
            return data;
        }

        public void setData(List<Double> data) {
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
}
