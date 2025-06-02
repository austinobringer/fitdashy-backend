package com.fitdashy.fitdashy_backend.payload.responses;

public class MealIngredientResponse {

    private Integer ingredientId;
    private String ingredientName;
    private Double weight;
    private Double calories;
    private Double protein;
    private Double carbs;
    private Double fat;

    // Constructors, Getters, and Setters
    public MealIngredientResponse() {
    }

    public MealIngredientResponse(Integer ingredientId, String ingredientName, Double weight, Double calories, Double protein, Double carbs, Double fat) {
        this.ingredientId = ingredientId;
        this.ingredientName = ingredientName;
        this.weight = weight;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
    }

    public Integer getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Integer ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getCalories() {
        return calories;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }

    public Double getProtein() {
        return protein;
    }

    public void setProtein(Double protein) {
        this.protein = protein;
    }

    public Double getCarbs() {
        return carbs;
    }

    public void setCarbs(Double carbs) {
        this.carbs = carbs;
    }

    public Double getFat() {
        return fat;
    }

    public void setFat(Double fat) {
        this.fat = fat;
    }
}