package com.ghostFood.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by android1 on 5/25/2017.
 */

public interface AddUserAPI {

    @FormUrlEncoded
    @POST("customer")
    Call<ResponseAddUser> AddUser(
            @Field("from_customer_key") String mFromUserKey,
            @Field("to_customer_key") String mToUserKey
    );
    public class ResponseAddUser {

        private Integer status;
        private Data data;
        private Integer time;
        private Integer response_code;
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

        public Integer getResponse_code() {
            return response_code;
        }

        public void setResponse_code(Integer response_code) {
            this.response_code = response_code;
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
