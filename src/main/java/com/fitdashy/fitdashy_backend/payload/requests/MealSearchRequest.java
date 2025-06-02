package com.fitdashy.fitdashy_backend.payload.requests;

import java.util.List;

public class MealSearchRequest {

    private String name;
    private Integer amount;
    private Integer minCalories;
    private Integer maxCalories;
    private Double minCarbs;
    private Double maxCarbs;
    private Double minProtein;
    private Double maxProtein;
    private Double minFat;
    private Double maxFat;
    private List<Integer> ingredientIds;

    // Default constructor (required for @ModelAttribute binding)
    public MealSearchRequest() {
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getMinCalories() {
        return minCalories;
    }

    public void setMinCalories(Integer minCalories) {
        this.minCalories = minCalories;
    }

    public Integer getMaxCalories() {
        return maxCalories;
    }

    public void setMaxCalories(Integer maxCalories) {
        this.maxCalories = maxCalories;
    }

    public Double getMinCarbs() {
        return minCarbs;
    }

    public void setMinCarbs(Double minCarbs) {
        this.minCarbs = minCarbs;
    }

    public Double getMaxCarbs() {
        return maxCarbs;
    }

    public void setMaxCarbs(Double maxCarbs) {
        this.maxCarbs = maxCarbs;
    }

    public Double getMinProtein() {
        return minProtein;
    }

    public void setMinProtein(Double minProtein) {
        this.minProtein = minProtein;
    }

    public Double getMaxProtein() {
        return maxProtein;
    }

    public void setMaxProtein(Double maxProtein) {
        this.maxProtein = maxProtein;
    }

    public Double getMinFat() {
        return minFat;
    }

    public void setMinFat(Double minFat) {
        this.minFat = minFat;
    }

    public Double getMaxFat() {
        return maxFat;
    }

    public void setMaxFat(Double maxFat) {
        this.maxFat = maxFat;
    }

    public List<Integer> getIngredientIds() {
        return ingredientIds;
    }

    public void setIngredientIds(List<Integer> ingredientIds) {
        this.ingredientIds = ingredientIds;
    }
}