package com.ghostFood.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Tech on 19-Apr-18.
 */

public interface GalleryApi {
    @GET("configuration/gallery")
    Call<GalleryApi.GalleryApiResponse> Get();

    public class GalleryApiResponse {

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

            @SerializedName("gallery_id")
            @Expose
            private Integer gallery_id;
            @SerializedName("gallery_image_path")
            @Expose
            private String gallery_image_path;

            public Integer getGallery_id() {
                return gallery_id;
            }

            public void setGallery_id(Integer gallery_id) {
                this.gallery_id = gallery_id;
            }

            public String getGallery_image_path() {
                return gallery_image_path;
            }

            public void setGallery_image_path(String gallery_image_path) {
                this.gallery_image_path = gallery_image_path;
            }

        }
    }
}
