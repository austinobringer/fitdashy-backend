package com.fitdashy.fitdashy_backend.payload.responses;

import java.util.List;

public class MealResponse {

    private Integer mealId;
    private String name;
    private Integer amount; // Or perhaps a range?  Consider minAmount and maxAmount
    private Double totalCalories;
    private Double totalCarbs;
    private Double totalProtein;
    private Double totalFat;
    private List<MealIngredientResponse> ingredients;

    // Constructors, Getters, and Setters
    public MealResponse() {
    }

    public MealResponse(Integer mealId, String name, Integer amount, Double totalCalories, Double totalCarbs, Double totalProtein, Double totalFat, List<MealIngredientResponse> ingredients) {
        this.mealId = mealId;
        this.name = name;
        this.amount = amount;
        this.totalCalories = totalCalories;
        this.totalCarbs = totalCarbs;
        this.totalProtein = totalProtein;
        this.totalFat = totalFat;
        this.ingredients = ingredients;
    }

    public Integer getMealId() {
        return mealId;
    }

    public void setMealId(Integer mealId) {
        this.mealId = mealId;
    }

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

    public Double getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(Double totalCalories) {
        this.totalCalories = totalCalories;
    }

    public Double getTotalCarbs() {
        return totalCarbs;
    }

    public void setTotalCarbs(Double totalCarbs) {
        this.totalCarbs = totalCarbs;
    }

    public Double getTotalProtein() {
        return totalProtein;
    }

    public void setTotalProtein(Double totalProtein) {
        this.totalProtein = totalProtein;
    }

    public Double getTotalFat() {
        return totalFat;
    }

    public void setTotalFat(Double totalFat) {
        this.totalFat = totalFat;
    }

    public List<MealIngredientResponse> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<MealIngredientResponse> ingredients) {
        this.ingredients = ingredients;
    }
}