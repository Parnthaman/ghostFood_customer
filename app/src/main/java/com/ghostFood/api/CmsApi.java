package com.ghostFood.api;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CmsApi {
    @GET("cms")
    Call<CmsApi.CmsApiResponse> Get();

    public class CmsApiResponse {

        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("data")
        @Expose
        private List<Datum> data = null;
        @SerializedName("time")
        @Expose
        private Integer time;
        @SerializedName("message")
        @Expose
        private String message;

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public List<Datum> getData() {
            return data;
        }

        public void setData(List<Datum> data) {
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


        public class Datum {

            @SerializedName("cms_key")
            @Expose
            private String cms_key;
            @SerializedName("cms_title")
            @Expose
            private String cms_title;
            @SerializedName("cms_content")
            @Expose
            private String cms_content;
            @SerializedName("cms_url")
            @Expose
            private String cms_url;

            public String getCms_key() {
                return cms_key;
            }

            public void setCms_key(String cms_key) {
                this.cms_key = cms_key;
            }

            public String getCms_title() {
                return cms_title;
            }

            public void setCms_title(String cms_title) {
                this.cms_title = cms_title;
            }

            public String getCms_content() {
                return cms_content;
            }

            public void setCms_content(String cms_content) {
                this.cms_content = cms_content;
            }

            public String getCms_url() {
                return cms_url;
            }

            public void setCms_url(String cms_url) {
                this.cms_url = cms_url;
            }

        }

    }

}