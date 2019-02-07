package com.ghostFood.api;

import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by android1 on 5/25/2017.
 */

public interface LoyaltyPointsAPI {

    @GET("user/loyalty-points")
    Call<ResponseLoyalityPoints> Get(@Query("user_key") String mUserKey);
    public class ResponseLoyalityPoints {

        @SerializedName("status")
        private Integer status;

        @SerializedName("data")
        private Data data = null;

        @SerializedName("time")
        private Integer time;

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

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }

    public class Data {

        @SerializedName("loyalty_points")
        private String loyaltyPoints;

        public String getLoyaltyPoints() {
            return loyaltyPoints;
        }

        public void setLoyaltyPoints(String loyalityPoints) {
            this.loyaltyPoints = loyalityPoints;
        }
    }
}
