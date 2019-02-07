package com.ghostFood.api;

import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by android1 on 5/27/2017.
 */

public interface PrivacyPolicyAPI {
    @GET("cms/privacy-policy")
    Call<ResponsePP> Get();

    public class ResponsePP {

        private Integer status;
        private Data data = null;
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

        @SerializedName("title")
        private String title;

        @SerializedName("content")
        private String content;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
