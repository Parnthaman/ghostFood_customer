package com.ghostFood.model;

import com.ghostFood.util.ConstantsInternal;

/**
 * Created by Tech on 6/15/2017.
 */

public class SpecialOffer {

    public String getOfferKey() {
        return offerKey;
    }

    public void setOfferKey(String offerKey) {
        this.offerKey = offerKey;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getOffername() {
        return offername;
    }

    public void setOffername(String offername) {
        this.offername = offername;
    }

    public String getOfferDescription() {
        return offerDescription;
    }

    public void setOfferDescription(String offerDescription) {
        this.offerDescription = offerDescription;
    }

    public Double getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(Double offerPrice) {
        this.offerPrice = offerPrice;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    public ConstantsInternal.OfferType getOfferType() {
        return offerType;
    }

    public void setOfferType(ConstantsInternal.OfferType offerType) {
        this.offerType = offerType;
    }

    private String offerKey;
    private String heading;
    private String offername,offerDescription;
    private String itemImage;
    private Boolean isSelected;
    private Double offerPrice;
    private ConstantsInternal.OfferType offerType;





}
