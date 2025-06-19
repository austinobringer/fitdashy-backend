package com.fitdashy.fitdashy_backend.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "meal_ingredients")
public class MealIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meal_ingredient_id")
    private Integer mealIngredientId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meal_id", nullable = false)
    private Meal meal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    @Column(name = "weight", nullable = false, precision = 6, scale = 1)
    private BigDecimal weight;

    // Constructors
    public MealIngredient() {}

    public MealIngredient(Meal meal, Ingredient ingredient, BigDecimal weight) {
        this.meal = meal;
        this.ingredient = ingredient;
        this.weight = weight;
    }

    // Getters and Setters
    public Integer getMealIngredientId() {
        return mealIngredientId;
    }

    public void setMealIngredientId(Integer mealIngredientId) {
        this.mealIngredientId = mealIngredientId;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }
}