package com.ghostFood.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.PUT;

/**
 * Created by android1 on 5/25/2017.
 */

public interface UpdateAddressAPI {

    @FormUrlEncoded
    @PUT("user-address/update")
    Call<ResponseUpate> Update(
            @Field("user_key") String mUserKey,
            @Field("user_address_key") String mUserAddressKey,
            @Field("address_contact_name") String mAddressContactName,
            @Field("building_name") String mBuildingName,
            @Field("flat_no") String mFlatRoomNo,
            @Field("address_line1") String mAddressLine1,
            @Field("address_line2") String mAddressLine2,
            @Field("area") String mArea,
            @Field("city") String mCity,
            @Field("country") String mCountry,
            @Field("zip_code") String mZipCode,
            @Field("latitude") Double mLatitude,
            @Field("longitude") Double mLongitude
    );

    public class ResponseUpate  {

        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("data")
        @Expose
        private List<Object> data = null;
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
