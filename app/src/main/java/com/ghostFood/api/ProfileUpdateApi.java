package com.ghostFood.api;

import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by android1 on 5/25/2017.
 */

public interface ProfileUpdateApi {
    @FormUrlEncoded
    @POST("user/update-profile")
    Call<ResponseProfileUpdate> Update(
            @Field("user_key") String mUserKey,
            @Field("first_name") String mFirstName,
            @Field("last_name") String mLastName,
            @Field("mobile_number") String mMobileNumber,
            @Field("email") String mEmailAddress);

    public class ResponseProfileUpdate {
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
