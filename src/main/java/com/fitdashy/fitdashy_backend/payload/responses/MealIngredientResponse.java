package com.fitdashy.fitdashy_backend.payload.responses;

public class MealIngredientResponse {

    private Integer id;
    private String name;
    private Double weight;
    private Double calories;
    private Double protein;
    private Double carbs;
    private Double fat;

    // Constructors, Getters, and Setters
    public MealIngredientResponse() {
    }

    public MealIngredientResponse(Integer id, String name, Double weight, Double calories, Double protein, Double carbs, Double fat) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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