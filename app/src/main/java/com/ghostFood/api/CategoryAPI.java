package com.ghostFood.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by android1 on 5/25/2017.
 */

public interface CategoryAPI {

    @GET("category")
    Call<ResponseCategory> Get(@Query("order_type") String mAddressType,
                               @Query("latitude") String mLatitude,
                               @Query("longitude") String mLongitude,
                               @Query("address_key") String mAddressKey,
                               @Query("branch_key") String mBranchKey,
                               @Query("user_key") String mUserKey);
    public class ResponseCategory {

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

        @SerializedName("category_key")
        private String categoryKey;

        @SerializedName("category_name")
        private String categoryName;

        @SerializedName("category_image")
        private String categoryImage;

        @SerializedName("category_status")
        private Integer categoryStatus;

        @SerializedName("language_code")
        private String languageCode;

        @SerializedName("items")
        private List<Item> item = null;

        public String getCategoryKey() {
            return categoryKey;
        }

        public void setCategoryKey(String categoryKey) {
            this.categoryKey = categoryKey;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getCategoryImage() {
            return categoryImage;
        }

        public void setCategoryImage(String categoryImage) {
            this.categoryImage = categoryImage;
        }

        public Integer getCategoryStatus() {
            return categoryStatus;
        }

        public void setCategoryStatus(Integer categoryStatus) {
            this.categoryStatus = categoryStatus;
        }

        public String getLanguageCode() {
            return languageCode;
        }

        public void setLanguageCode(String languageCode) {
            this.languageCode = languageCode;
        }

        public List<Item> getItems() {
            return item;
        }

        public void setItems(List<Item> item) {
            this.item = item;
        }
    }

    public class Item {

        @SerializedName("item_key")
        private String itemKey;

        @SerializedName("item_name")
        private String itemName;

        @SerializedName("item_price")
        private String item_price;

        @SerializedName("food_type")
        private Integer food_type;

        @SerializedName("item_image")
        private List<ItemImage> itemImage = null;

        public String getItemKey() {
            return itemKey;
        }

        public void setItemKey(String itemKey) {
            this.itemKey = itemKey;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public List<ItemImage> getItemImage() {
            return itemImage;
        }

        public void setItemImage(List<ItemImage> itemImage) {
            this.itemImage = itemImage;
        }

        public String getItem_price() {
            return item_price;
        }

        public void setItem_price(String item_price) {
            this.item_price = item_price;
        }

        public Integer getFood_type() {
            return food_type;
        }

        public void setFood_type(Integer food_type) {
            this.food_type = food_type;
        }
    }

    public class ItemImage {

        @SerializedName("item_image_path")
        private String itemImagePath;

        public String getItemImagePath() {
            return itemImagePath;
        }

        public void setItemImagePath(String itemImagePath) {
            this.itemImagePath = itemImagePath;
        }

    }
}
