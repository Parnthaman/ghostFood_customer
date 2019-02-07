package com.ghostFood.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by android1 on 5/25/2017.
 */

public interface OrderDetailsAPI {

    @GET("order/details")
    Call<ResponseOrderDetails> Get(@Query("order_key") String mOrderKey);

    public class ResponseOrderDetails {

        @SerializedName("status")
        private Integer status;
        @SerializedName("data")
        private Data data;
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

        @SerializedName("payment_details")
        private List<PaymentDetail> paymentDetails = null;
        @SerializedName("order_details")
        private OrderDetails orderDetails;

        public List<PaymentDetail> getPaymentDetails() {
            return paymentDetails;
        }

        public void setPaymentDetails(List<PaymentDetail> paymentDetails) {
            this.paymentDetails = paymentDetails;
        }

        public OrderDetails getOrderDetails() {
            return orderDetails;
        }

        public void setOrderDetails(OrderDetails orderDetails) {
            this.orderDetails = orderDetails;
        }


    }

    public class OrderDetails {
        @SerializedName("order_key")
        private String orderKey;
        @SerializedName("order_number")
        private String orderNumber;
        @SerializedName("order_status")
        private String orderStatus;
        @SerializedName("order_date_time")
        private String orderDateTime;

        @SerializedName("order_total")
        private Double orderTotal;

        @SerializedName("sub_total_price")
        private Double subTotalPrice;

        @SerializedName("delivery_price")
        private Double deliveryPrice;

        @SerializedName("total_price")
        private Double totalPrice;

        @SerializedName("items")
        private List<Item> items = null;

        @SerializedName("offers")
        private List<Offer> offers = null;

        @SerializedName("coupons")
        private List<Coupon> coupons = null;

        @SerializedName("branch")
        private List<Branch> branch = null;

        @SerializedName("latitude")
        private Double latitude;

        @SerializedName("longitude")
        private Double longitude;


        public List<Branch> getBranch() {
            return branch;
        }

        public void setBranch(List<Branch> branch) {
            this.branch = branch;
        }

        public Double getLatitude() {
            return latitude;
        }

        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }

        public Double getLongitude() {
            return longitude;
        }

        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }

        public String getOrderKey() {
            return orderKey;
        }

        public void setOrderKey(String orderKey) {
            this.orderKey = orderKey;
        }

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getOrderDateTime() {
            return orderDateTime;
        }

        public void setOrderDateTime(String orderDateTime) {
            this.orderDateTime = orderDateTime;
        }

        public Double getOrderTotal() {
            return orderTotal;
        }

        public void setOrderTotal(Double orderTotal) {
            this.orderTotal = orderTotal;
        }

        public List<Item> getItems() {
            return items;
        }

        public void setItems(List<Item> items) {
            this.items = items;
        }

        public Double getSubTotalPrice() {
            return subTotalPrice;
        }

        public void setSubTotalPrice(Double subTotalPrice) {
            this.subTotalPrice = subTotalPrice;
        }

        public Double getDeliveryPrice() {
            return deliveryPrice;
        }

        public void setDeliveryPrice(Double deliveryPrice) {
            this.deliveryPrice = deliveryPrice;
        }

        public Double getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(Double totalPrice) {
            this.totalPrice = totalPrice;
        }

        public List<Offer> getOffers() {
            return offers;
        }

        public void setOffers(List<Offer> offers) {
            this.offers = offers;
        }

        public List<Coupon> getCoupons() {
            return coupons;
        }

        public void setCoupons(List<Coupon> coupons) {
            this.coupons = coupons;
        }
    }

    public class PaymentDetail {

        @SerializedName("name")
        private String name;
        @SerializedName("value")
        private Double value;
        @SerializedName("symbol")
        private String symbol;
        @SerializedName("color")
        private Boolean color;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Double getValue() {
            return value;
        }

        public void setValue(Double value) {
            this.value = value;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public Boolean getColor() {
            return color;
        }

        public void setColor(Boolean color) {
            this.color = color;
        }

    }

    public class Item {

        @SerializedName("quantity")
        private Integer quantity;

        @SerializedName("item_key")
        private String itemKey;

        @SerializedName("item_name")
        private String itemName;

        @SerializedName("item_image")
        private String itemImage;

        @SerializedName("item_price")
        private Double itemPrice;

        @SerializedName("total_price")
        private Double totalPrice;

        @SerializedName("ingredients_name")
        private String ingredientsName;

        @SerializedName("item_images")
        private List<ItemImage> itemImages = null;

        @SerializedName("ingredients_group")
        private List<IngredientsGroup> ingredientsGroup = null;

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

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

        public Double getItemPrice() {
            return itemPrice;
        }

        public void setItemPrice(Double itemPrice) {
            this.itemPrice = itemPrice;
        }

        public String getItemImage() {
            return itemImage;
        }

        public void setItemImage(String itemImage) {
            this.itemImage = itemImage;
        }

        public Double getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(Double totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getIngredientsName() {
            return ingredientsName;
        }

        public void setIngredientsName(String ingredientsName) {
            this.ingredientsName = ingredientsName;
        }

        public List<IngredientsGroup> getIngredientsGroup() {
            return ingredientsGroup;
        }

        public void setIngredientsGroup(List<IngredientsGroup> ingredientsGroup) {
            this.ingredientsGroup = ingredientsGroup;
        }

        public List<ItemImage> getItemImages() {
            return itemImages;
        }

        public void setItemImages(List<ItemImage> itemImages) {
            this.itemImages = itemImages;
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

    public class Coupon {

        @SerializedName("coupon_type")
        private Integer couponType;

        @SerializedName("coupon_value")
        private Double couponValue;

        @SerializedName("coupon_key")
        private String couponKey;

        @SerializedName("coupon_name")
        private String couponName;

        @SerializedName("coupon_description")
        private String couponDescription;

        @SerializedName("coupon_image")
        private String couponImage;

        @SerializedName("coupon_scope")
        private Integer couponScope;

        public String getCouponName() {
            return couponName;
        }

        public Integer getCouponType() {
            return couponType;
        }

        public void setCouponType(Integer couponType) {
            this.couponType = couponType;
        }

        public Double getCouponValue() {
            return couponValue;
        }

        public void setCouponValue(Double couponValue) {
            this.couponValue = couponValue;
        }

        public String getCouponKey() {
            return couponKey;
        }

        public void setCouponKey(String couponKey) {
            this.couponKey = couponKey;
        }

        public void setCouponName(String couponName) {
            this.couponName = couponName;
        }

        public String getCouponDescription() {
            return couponDescription;
        }

        public void setCouponDescription(String couponDescription) {
            this.couponDescription = couponDescription;
        }

        public String getCouponImage() {
            return couponImage;
        }

        public void setCouponImage(String couponImage) {
            this.couponImage = couponImage;
        }

        public Integer getCouponScope() {
            return couponScope;
        }

        public void setCouponScope(Integer couponScope) {
            this.couponScope = couponScope;
        }
    }

    public class Offer {

        @SerializedName("offer_type")
        private Integer offerType;

        @SerializedName("offer_price")
        private Double offerPrice;

        @SerializedName("offer_key")
        private String offerKey;

        @SerializedName("offer_title")
        private String offerTitle;

        @SerializedName("offer_image")
        private String offerImage;

        @SerializedName("offer_text1")
        private String offerText1;

        @SerializedName("offer_text2")
        private String offerText2;

        @SerializedName("offer_text3")
        private String offerText3;

        @SerializedName("offer_description")
        private String offerDescription;

        @SerializedName("quantity")
        private Integer quantity;

        public Integer getOfferType() {
            return offerType;
        }

        public void setOfferType(Integer offerType) {
            this.offerType = offerType;
        }

        public String getOfferTitle() {
            return offerTitle;
        }

        public Double getOfferPrice() {
            return offerPrice;
        }

        public void setOfferPrice(Double offerPrice) {
            this.offerPrice = offerPrice;
        }

        public String getOfferKey() {
            return offerKey;
        }

        public void setOfferKey(String offerKey) {
            this.offerKey = offerKey;
        }

        public void setOfferTitle(String offerTitle) {
            this.offerTitle = offerTitle;
        }

        public String getOfferImage() {
            return offerImage;
        }

        public void setOfferImage(String offerImage) {
            this.offerImage = offerImage;
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

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }
    }


    public class Ingredient {

        @SerializedName("order_item_ingredient_group_id")
        private String orderItemIngredientGroupId;

        @SerializedName("ingredient_id")
        private String ingredientId;

        @SerializedName("ingredient_key")
        private String ingredientKey;

        @SerializedName("price")
        private Double ingredientPrice;

        @SerializedName("is_selected_drink")
        private Integer isSelectedDrink;

        @SerializedName("ingredients")
        private List<Ingredient_> ingredients = null;

        public String getOrderItemIngredientGroupId() {
            return orderItemIngredientGroupId;
        }

        public void setOrderItemIngredientGroupId(String orderItemIngredientGroupId) {
            this.orderItemIngredientGroupId = orderItemIngredientGroupId;
        }

        public String getIngredientId() {
            return ingredientId;
        }

        public void setIngredientId(String ingredientId) {
            this.ingredientId = ingredientId;
        }

        public String getIngredientKey() {
            return ingredientKey;
        }

        public void setIngredientKey(String ingredientKey) {
            this.ingredientKey = ingredientKey;
        }

        public Double getIngredientPrice() {
            return ingredientPrice;
        }

        public void setIngredientPrice(Double ingredientPrice) {
            this.ingredientPrice = ingredientPrice;
        }

        public Integer getIsSelectedDrink() {
            return isSelectedDrink;
        }

        public void setIsSelectedDrink(Integer isSelectedDrink) {
            this.isSelectedDrink = isSelectedDrink;
        }

        public List<Ingredient_> getIngredients() {
            return ingredients;
        }

        public void setIngredients(List<Ingredient_> ingredients) {
            this.ingredients = ingredients;
        }

    }

    public class Ingredient_ {

        @SerializedName("order_item_ingredient_mapping_lang_id")
        private String orderItemIngredientMappingLangId;
        @SerializedName("order_item_ingredient_mapping_id")
        private String orderItemIngredientMappingId;
        @SerializedName("language_code")
        private String languageCode;
        @SerializedName("ingredient_name")
        private String ingredientName;
        @SerializedName("ingredient_description")
        private String ingredientDescription;
        @SerializedName("ingredient_image")
        private String ingredientImage;

        public String getOrderItemIngredientMappingLangId() {
            return orderItemIngredientMappingLangId;
        }

        public void setOrderItemIngredientMappingLangId(String orderItemIngredientMappingLangId) {
            this.orderItemIngredientMappingLangId = orderItemIngredientMappingLangId;
        }

        public String getOrderItemIngredientMappingId() {
            return orderItemIngredientMappingId;
        }

        public void setOrderItemIngredientMappingId(String orderItemIngredientMappingId) {
            this.orderItemIngredientMappingId = orderItemIngredientMappingId;
        }

        public String getLanguageCode() {
            return languageCode;
        }

        public void setLanguageCode(String languageCode) {
            this.languageCode = languageCode;
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

    public class IngredientsGroup {

        @SerializedName("ingredient_type")
        private Integer ingredientType;

        @SerializedName("order_item_ingredient_group_id")
        private String orderItemIngredientGroupId;
        @SerializedName("item_ingredient_group_key")
        private String itemIngredientGroupKey;
        @SerializedName("ingredients")
        private List<Ingredient> ingredients = null;

        public Integer getIngredientType() {
            return ingredientType;
        }

        public void setIngredientType(Integer ingredientType) {
            this.ingredientType = ingredientType;
        }

        public String getOrderItemIngredientGroupId() {
            return orderItemIngredientGroupId;
        }

        public void setOrderItemIngredientGroupId(String orderItemIngredientGroupId) {
            this.orderItemIngredientGroupId = orderItemIngredientGroupId;
        }

        public String getItemIngredientGroupKey() {
            return itemIngredientGroupKey;
        }

        public void setItemIngredientGroupKey(String itemIngredientGroupKey) {
            this.itemIngredientGroupKey = itemIngredientGroupKey;
        }

        public List<Ingredient> getIngredients() {
            return ingredients;
        }

        public void setIngredients(List<Ingredient> ingredients) {
            this.ingredients = ingredients;
        }

    }

    public class Branch {

        @SerializedName("branch_key")
        @Expose
        private String branch_key;
        @SerializedName("latitude")
        @Expose
        private String latitude;
        @SerializedName("longitude")
        @Expose
        private String longitude;
        @SerializedName("contact_email")
        @Expose
        private String contact_email;
        @SerializedName("contact_number1")
        @Expose
        private String contact_number1;
        @SerializedName("contact_number2")
        @Expose
        private Object contact_number2;
        @SerializedName("currency_id")
        @Expose
        private Object currency_id;
        @SerializedName("pickup_time")
        @Expose
        private Object pickup_time;
        @SerializedName("delivery_time")
        @Expose
        private Object delivery_time;
        @SerializedName("branch_status")
        @Expose
        private Integer branch_status;
        @SerializedName("approve_status")
        @Expose
        private Integer approve_status;
        @SerializedName("branch_availability_status")
        @Expose
        private Integer branch_availability_status;
        @SerializedName("branch_name")
        @Expose
        private String branch_name;
        @SerializedName("branch_address_line1")
        @Expose
        private String branch_address_line1;
        @SerializedName("branch_address_line2")
        @Expose
        private String branch_address_line2;
        @SerializedName("area")
        @Expose
        private String area;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("access_token")
        @Expose
        private String access_token;
        @SerializedName("distance")
        @Expose
        private Integer distance;

        public String getBranch_key() {
            return branch_key;
        }

        public void setBranch_key(String branch_key) {
            this.branch_key = branch_key;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getContact_email() {
            return contact_email;
        }

        public void setContact_email(String contact_email) {
            this.contact_email = contact_email;
        }

        public String getContact_number1() {
            return contact_number1;
        }

        public void setContact_number1(String contact_number1) {
            this.contact_number1 = contact_number1;
        }

        public Object getContact_number2() {
            return contact_number2;
        }

        public void setContact_number2(Object contact_number2) {
            this.contact_number2 = contact_number2;
        }

        public Object getCurrency_id() {
            return currency_id;
        }

        public void setCurrency_id(Object currency_id) {
            this.currency_id = currency_id;
        }

        public Object getPickup_time() {
            return pickup_time;
        }

        public void setPickup_time(Object pickup_time) {
            this.pickup_time = pickup_time;
        }

        public Object getDelivery_time() {
            return delivery_time;
        }

        public void setDelivery_time(Object delivery_time) {
            this.delivery_time = delivery_time;
        }

        public Integer getBranch_status() {
            return branch_status;
        }

        public void setBranch_status(Integer branch_status) {
            this.branch_status = branch_status;
        }

        public Integer getApprove_status() {
            return approve_status;
        }

        public void setApprove_status(Integer approve_status) {
            this.approve_status = approve_status;
        }

        public Integer getBranch_availability_status() {
            return branch_availability_status;
        }

        public void setBranch_availability_status(Integer branch_availability_status) {
            this.branch_availability_status = branch_availability_status;
        }

        public String getBranch_name() {
            return branch_name;
        }

        public void setBranch_name(String branch_name) {
            this.branch_name = branch_name;
        }

        public String getBranch_address_line1() {
            return branch_address_line1;
        }

        public void setBranch_address_line1(String branch_address_line1) {
            this.branch_address_line1 = branch_address_line1;
        }

        public String getBranch_address_line2() {
            return branch_address_line2;
        }

        public void setBranch_address_line2(String branch_address_line2) {
            this.branch_address_line2 = branch_address_line2;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public Integer getDistance() {
            return distance;
        }

        public void setDistance(Integer distance) {
            this.distance = distance;
        }

    }
}
