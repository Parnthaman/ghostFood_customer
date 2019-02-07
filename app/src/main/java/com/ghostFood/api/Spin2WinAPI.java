package com.ghostFood.api;

import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by android1 on 5/25/2017.
 */

public interface Spin2WinAPI {

    @GET("spin-wheel")
    Call<ResponseSpin2Win> Get(@Query("user_key") String mUserKey);

    public class ResponseSpin2Win {

        @SerializedName("status")
        private Integer status;

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
