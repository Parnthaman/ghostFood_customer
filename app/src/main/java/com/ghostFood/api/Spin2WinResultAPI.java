package com.ghostFood.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by android1 on 5/25/2017.
 */

public interface Spin2WinResultAPI {

    @FormUrlEncoded
    @POST("spin-wheel/result")
    Call<ResponseSpin2Win> Update(
            @Field("user_key") String mUserKey,
            @Field("type") String mType,
            @Field("win_status") String mWinStatus,
            @Field("win_type") String mUserDataType
    );

    public class ResponseSpin2Win {

        @SerializedName("status")
        private Integer status;

        @SerializedName("data")
        private List<Data> data = null;

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

        public List<Data> getData() {
            return data;
        }

        public void setData(List<Data> data) {
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
    }
}