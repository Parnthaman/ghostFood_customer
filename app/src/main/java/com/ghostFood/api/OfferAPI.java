package com.ghostFood.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by android1 on 5/25/2017.
 */

public interface OfferAPI {

    @GET("offer")
    Call<ResponseOffer> Get(@Query("offer_type") Integer mOfferType);
    public class ResponseOffer {

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

        @SerializedName("offer_key")
        private String offerKey;
        @SerializedName("sort_no")
        private Integer sortNo;
        @SerializedName("offer_type")
        private Integer offerType;
        @SerializedName("offer_price")
        private Double offerPrice;
        @SerializedName("offer_title")
        private String offerTitle;
        @SerializedName("items")
        private List<Item> items = null;
        @SerializedName("offer_image")
        private String offerImage;
        @SerializedName("offer_item_image")
        private String offerItemImage;
        @SerializedName("offer_text1")
        private String offerText1;
        @SerializedName("offer_text2")
        private String offerText2;
        @SerializedName("offer_text3")
        private String offerText3;
        @SerializedName("offer_description")
        private String offerDescription;

        public String getOfferKey() {
            return offerKey;
        }

        public void setOfferKey(String offerKey) {
            this.offerKey = offerKey;
        }

        public Integer getSortNo() {
            return sortNo;
        }

        public void setSortNo(Integer sortNo) {
            this.sortNo = sortNo;
        }

        public Integer getOfferType() {
            return offerType;
        }

        public void setOfferType(Integer offerType) {
            this.offerType = offerType;
        }

        public Double getOfferPrice() {
            return offerPrice;
        }

        public void setOfferPrice(Double offerPrice) {
            this.offerPrice = offerPrice;
        }

        public String getOfferTitle() {
            return offerTitle;
        }

        public void setOfferTitle(String offerTitle) {
            this.offerTitle = offerTitle;
        }

        public List<Item> getItems() {
            return items;
        }

        public void setItems(List<Item> items) {
            this.items = items;
        }

        public String getOfferImage() {
            return offerImage;
        }

        public void setOfferImage(String offerImage) {
            this.offerImage = offerImage;
        }

        public String getOfferItemImage() {
            return offerItemImage;
        }

        public void setOfferItemImage(String offerItemImage) {
            this.offerItemImage = offerItemImage;
        }

        public String getOfferText1() {
            return offerText1;
        }

        public void setOfferText1(String offerText1) {
            this.offerText1 = offerText1;
        }

        public String getOfferText2() {
            return offerText2;
        }

        public void setOfferText2(String offerText2) {
            this.offerText2 = offerText2;
        }

        public String getOfferText3() {
            return offerText3;
        }

        public void setOfferText3(String offerText3) {
            this.offerText3 = offerText3;
        }

        public String getOfferDescription() {
            return offerDescription;
        }

        public void setOfferDescription(String offerDescription) {
            this.offerDescription = offerDescription;
        }
    }

    public class Item {

        @SerializedName("offer_item_id")
        private String offerItemId;
        @SerializedName("offer_id")
        private String offerId;
        @SerializedName("item_id")
        private String itemId;
        @SerializedName("item_count")
        private Integer itemCount;

        public String getOfferItemId() {
            return offerItemId;
        }

        public void setOfferItemId(String offerItemId) {
            this.offerItemId = offerItemId;
        }

        public String getOfferId() {
            return offerId;
        }

        public void setOfferId(String offerId) {
            this.offerId = offerId;
        }

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public Integer getItemCount() {
            return itemCount;
        }

        public void setItemCount(Integer itemCount) {
            this.itemCount = itemCount;
        }

    }

    /*public class Items {

        @SerializedName("item_key")
        private String itemKey;

        @SerializedName("item_name")
        private String itemName;

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

    }*/
}
