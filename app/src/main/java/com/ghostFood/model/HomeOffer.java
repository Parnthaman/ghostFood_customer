package com.ghostFood.model;

import com.ghostFood.util.ConstantsInternal;

/**
 * Created by Tech on 6/15/2017.
 */

public class HomeOffer {

    private String key;
    private ConstantsInternal.OfferType offerType;
    private String heading;
    private String itemName;
    private Double itemPrice;
    private String offerText;
    private String itemImage;
    private String offerImage;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ConstantsInternal.OfferType getOfferType() {
        return offerType;
    }

    public void setOfferType(ConstantsInternal.OfferType offerType) {
        this.offerType = offerType;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
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

    public String getOfferText() {
        return offerText;
    }

    public void setOfferText(String offerText) {
        this.offerText = offerText;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getOfferImage() {
        return offerImage;
    }

    public void setOfferImage(String offerImage) {
        this.offerImage = offerImage;
    }

}
