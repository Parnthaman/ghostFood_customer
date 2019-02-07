package com.ghostFood.model;

import java.io.Serializable;

/**
 * Created by Tech on 6/16/2017.
 */

public class Category implements Serializable{

    private String categoryName;
    private String categoryKey;
    private String categoryImage;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryKey() {
        return categoryKey;
    }

    public void setCategoryKey(String categoryKey) {
        this.categoryKey = categoryKey;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }


}
