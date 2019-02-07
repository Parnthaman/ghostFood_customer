package com.ghostFood.model;

/**
 * Created by android1 on 7/3/2017.
 */

public class Ingredient {
    private Boolean isSelected;

    private String ingredientKey;

    private String ingredientName;

    private String ingredientDescription;

    private String ingredientImage;

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

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
