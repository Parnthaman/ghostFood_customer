package com.ghostFood.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by android1 on 5/25/2017.
 */

public interface AddedRequestStatusAPI {

    @FormUrlEncoded
    @POST("user/update-request-status")
    Call<ResponseAddedRequestStatus> Update(
            @Field("from_customer_key") String mFromUserKey,
            @Field("to_customer_key") String mToUserKey,
            @Field("request_status") Integer mRequestStatus
    );
    public class ResponseAddedRequestStatus {
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

    }
}
