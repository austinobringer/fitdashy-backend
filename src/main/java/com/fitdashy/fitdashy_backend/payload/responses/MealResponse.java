package com.fitdashy.fitdashy_backend.payload.responses;

import java.util.List;

public class MealResponse {

    private Integer id;
    private String name;
    private String owner;
    private Integer usage;
    private Double totalCalories;
    private Double totalCarbs;
    private Double totalProtein;
    private Double totalFat;
    private List<MealTagResponse> tags;
    private List<MealIngredientResponse> ingredients;

    // Constructors, Getters, and Setters
    public MealResponse() {
    }

    public MealResponse(Integer id, String name, String owner, Integer usage, Double totalCalories, Double totalCarbs, Double totalProtein, Double totalFat, List<MealTagResponse> tags, List<MealIngredientResponse> ingredients) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.usage = usage;
        this.totalCalories = totalCalories;
        this.totalCarbs = totalCarbs;
        this.totalProtein = totalProtein;
        this.totalFat = totalFat;
        this.tags = tags;
        this.ingredients = ingredients;
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Integer getUsage() {
        return usage;
    }

    public void setUsage(Integer usage) {
        this.usage = usage;
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

    public List<MealTagResponse> getTags() {
        return tags;
    }

    public void setTags(List<MealTagResponse> tags) {
        this.tags = tags;
    }

    public List<MealIngredientResponse> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<MealIngredientResponse> ingredients) {
        this.ingredients = ingredients;
    }
}