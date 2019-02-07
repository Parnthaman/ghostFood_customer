package com.ghostFood.model;

import java.util.List;

/**
 * Created by android1 on 7/3/2017.
 */

public class ItemIngredient {
    private Integer ingredientType;

    private Integer minimum;

    private Integer maximum;

    private String groupName;

    private List<Ingredient> ingredients = null;

    public Integer getIngredientType() {
        return ingredientType;
    }

    public void setIngredientType(Integer ingredientType) {
        this.ingredientType = ingredientType;
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
}
