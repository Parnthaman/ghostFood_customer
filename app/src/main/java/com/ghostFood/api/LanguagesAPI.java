package com.ghostFood.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by android1 on 5/25/2017.
 */

public interface LanguagesAPI {

    @GET("language")
    Call<ResponseLanguages> Get();
    public class ResponseLanguages {

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

        @SerializedName("language_key")
        private String languageKey;

        @SerializedName("language_name")
        private String languageName;

        @SerializedName("language_code")
        private String languageCode;

        @SerializedName("is_rtl")
        private Integer isRtl;

        public String getLanguageKey() {
            return languageKey;
        }

        public void setLanguageKey(String languageKey) {
            this.languageKey = languageKey;
        }

        public String getLanguageName() {
            return languageName;
        }

        public void setLanguageName(String languageName) {
            this.languageName = languageName;
        }

        public String getLanguageCode() {
            return languageCode;
        }

        public void setLanguageCode(String languageCode) {
            this.languageCode = languageCode;
        }

        public Integer getIsRtl() {
            return isRtl;
        }

        public void setIsRtl(Integer isRtl) {
            this.isRtl = isRtl;
        }
    }
}
