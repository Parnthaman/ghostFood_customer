package com.ghostFood.api;

import com.google.gson.annotations.SerializedName;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by android1 on 6/2/2017.
 */

public interface ProfileImageAddApi {
    @Multipart
    @POST("user/image")
    Call<ResponseProfileImageAdd> Register(
            @Part("user_key") RequestBody mUserKey,
            @Part MultipartBody.Part mProfileImage
            //@Part("profile_image") MultipartBody.Part mProfileImage
    );

    public class ResponseProfileImageAdd {
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
        @SerializedName("profile_image")
        private String profileImage;

        public String getProfileImage() {
            return profileImage;
        }

        public void setProfileImage(String profileImage) {
            this.profileImage = profileImage;
        }

    }
}
