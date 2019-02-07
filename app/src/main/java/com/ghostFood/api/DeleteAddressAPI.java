package com.ghostFood.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HTTP;

/**
 * Created by android1 on 5/25/2017.
 */

public interface DeleteAddressAPI {
    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "user-address/delete", hasBody = true)
    Call<ResponseDelete> Delete(
            @Field("user_key") String mUserKey,
            @Field("user_address_key") String mAddressKey
    );

    public class ResponseDelete {

        @SerializedName("status")
        private Integer status;

        @SerializedName("data")
        private List<Object> data = null;
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

        public List<Object> getData() {
            return data;
        }

        public void setData(List<Object> data) {
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
}
