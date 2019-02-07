package com.ghostFood.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by android1 on 5/25/2017.
 */

public interface ItemAPI {

    @GET("item")
    Call<ResponseItem> Get(@Query("item_key") String mItemKey);
    public class ResponseItem {

        @SerializedName("status")
        private Integer status;

        @SerializedName("data")
        private Data data = null;

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

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }

    public class Data {

        @SerializedName("item_key")
        private String itemKey;

        @SerializedName("sort_no")
        private Integer sortNo;

        @SerializedName("item_name")
        private String itemName;

        @SerializedName("item_description")
        private String itemDescription;

        @SerializedName("item_price")
        private Double itemPrice;

        @SerializedName("food_type")
        private Integer food_type;

        @SerializedName("item_image")
        private List<ItemImage> itemImage = null;

        @SerializedName("item_ingredient")
        private List<ItemIngredient> itemIngredient = null;

        public String getItemKey() {
            return itemKey;
        }

        public void setItemKey(String itemKey) {
            this.itemKey = itemKey;
        }

        public Integer getSortNo() {
            return sortNo;
        }

        public void setSortNo(Integer sortNo) {
            this.sortNo = sortNo;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getItemDescription() {
            return itemDescription;
        }

        public void setItemDescription(String itemDescription) {
            this.itemDescription = itemDescription;
        }

        public Double getItemPrice() {
            return itemPrice;
        }

        public void setItemPrice(Double itemPrice) {
            this.itemPrice = itemPrice;
        }

        public List<ItemIngredient> getItemIngredient() {
            return itemIngredient;
        }

        public void setItemIngredient(List<ItemIngredient> itemIngredient) {
            this.itemIngredient = itemIngredient;
        }

        public List<ItemImage> getItemImage() {
            return itemImage;
        }

        public void setItemImage(List<ItemImage> itemImage) {
            this.itemImage = itemImage;
        }

        public Integer getFood_type() {
            return food_type;
        }

        public void setFood_type(Integer food_type) {
            this.food_type = food_type;
        }
    }

    public class Details {

        @SerializedName("ingredient_key")
        private String ingredientKey;

        @SerializedName("ingredient_name")
        private String ingredientName;

        @SerializedName("ingredient_description")
        private String ingredientDescription;

        @SerializedName("ingredient_image")
        private String ingredientImage;

        public String getIngredientKey() {
            return ingredientKey;
        }

        public void setIngredientKey(String ingredientKey) {
            this.ingredientKey = ingredientKey;
        }

        public String getIngredientName() {
            return ingredientName;
        }

        public void setIngredientName(String ingredientName) {
            this.ingredientName = ingredientName;
        }

        public String getIngredientDescription() {
            return ingredientDescription;
        }

        public void setIngredientDescription(String ingredientDescription) {
            this.ingredientDescription = ingredientDescription;
        }

        public String getIngredientImage() {
            return ingredientImage;
        }

        public void setIngredientImage(String ingredientImage) {
            this.ingredientImage = ingredientImage;
        }
    }

    public class Ingredient {

        @SerializedName("is_selected_drink")
        private Boolean isSelectedDrink;

        @SerializedName("is_selected")
        private Boolean isSelected;

        @SerializedName("details")
        private Details details;

        @SerializedName("price")
        private Double price;

        private String groupKey;

        public Details getDetails() {
            return details;
        }

        public void setDetails(Details details) {
            this.details = details;
        }

        public Boolean getSelectedDrink() {
            return isSelectedDrink;
        }

        public void setSelectedDrink(Boolean selectedDrink) {
            isSelectedDrink = selectedDrink;
        }

        public Boolean getSelected() {
            return isSelected;
        }

        public void setSelected(Boolean selected) {
            isSelected = selected;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public String getGroupKey() {
            return groupKey;
        }

        public void setGroupKey(String groupKey) {
            this.groupKey = groupKey;
        }
    }

    public class ItemIngredient {

        @SerializedName("is_drink")
        private Integer isDrink;

        @SerializedName("item_ingredient_group_key")
        private String itemIngredientGroupKey;

        @SerializedName("ingredient_type")
        private Integer ingredientType;

        @SerializedName("minimum")
        private Integer minimum;

        @SerializedName("maximum")
        private Integer maximum;

        @SerializedName("group_name")
        private String groupName;

        @SerializedName("ingredients")
        private List<Ingredient> ingredients = null;

        public Integer getIsDrink() {
            return isDrink;
        }

        public void setIsDrink(Integer isDrink) {
            this.isDrink = isDrink;
        }

        private Integer selectedItemsCount = 0;

        public String getItemIngredientGroupKey() {
            return itemIngredientGroupKey;
        }

        public void setItemIngredientGroupKey(String itemIngredientGroupKey) {
            this.itemIngredientGroupKey = itemIngredientGroupKey;
        }

        public Integer getIngredientType() {
            return ingredientType;
        }

        public void setIngredientType(Integer ingredientType) {
            this.ingredientType = ingredientType;
        }

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public List<Ingredient> getIngredients() {
            return ingredients;
        }

        public void setIngredients(List<Ingredient> ingredients) {
            this.ingredients = ingredients;
        }

        public Integer getMinimum() {
            return minimum;
        }

        public void setMinimum(Integer minimum) {
            this.minimum = minimum;
        }

        public Integer getMaximum() {
            return maximum;
        }

        public void setMaximum(Integer maximum) {
            this.maximum = maximum;
        }

        public Integer getSelectedItemsCount() {
            return selectedItemsCount;
        }

        public void setSelectedItemsCount(Integer selectedItemsCount) {
            this.selectedItemsCount = selectedItemsCount;
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
