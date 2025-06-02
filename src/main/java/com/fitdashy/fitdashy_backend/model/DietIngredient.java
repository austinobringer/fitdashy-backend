package com.fitdashy.fitdashy_backend.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "diet_ingredients")
public class DietIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diet_ingredient_id")
    private Integer dietIngredientId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diet_meal_id", nullable = false)
    private DietMeal dietMeal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meal_ingredient_id", nullable = false)
    private MealIngredient mealIngredient;

    @Column(name = "weight", nullable = false, precision = 6, scale = 1)
    private BigDecimal weight;

    @Column(name = "calories", nullable = false, precision = 6, scale = 0)
    private Integer calories;

    @Column(name = "protein", precision = 6, scale = 1)
    private BigDecimal protein;

    @Column(name = "carbs", precision = 6, scale = 1)
    private BigDecimal carbs;

    @Column(name = "fat", precision = 6, scale = 1)
    private BigDecimal fat;

    // Constructors
    public DietIngredient() {}

    public DietIngredient(User user, DietMeal dietMeal, MealIngredient mealIngredient, BigDecimal weight, Integer calories, BigDecimal protein, BigDecimal carbs, BigDecimal fat) {
        this.user = user;
        this.dietMeal = dietMeal;
        this.mealIngredient = mealIngredient;
        this.weight = weight;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
    }

    // Getters and Setters
    public Integer getDietIngredientId() {
        return dietIngredientId;
    }

    public void setDietIngredientId(Integer dietIngredientId) {
        this.dietIngredientId = dietIngredientId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DietMeal getDietMeal() {
        return dietMeal;
    }

    public void setDietMeal(DietMeal dietMeal) {
        this.dietMeal = dietMeal;
    }

    public MealIngredient getMealIngredient() {
        return mealIngredient;
    }

    public void setMealIngredient(MealIngredient mealIngredient) {
        this.mealIngredient = mealIngredient;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public BigDecimal getProtein() {
        return protein;
    }

    public void setProtein(BigDecimal protein) {
        this.protein = protein;
    }

    public BigDecimal getCarbs() {
        return carbs;
    }

    public void setCarbs(BigDecimal carbs) {
        this.carbs = carbs;
    }

    public BigDecimal getFat() {
        return fat;
    }

    public void setFat(BigDecimal fat) {
        this.fat = fat;
    }
}