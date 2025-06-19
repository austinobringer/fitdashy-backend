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

    // Constructors
    public DietIngredient() {}

    public DietIngredient(User user, DietMeal dietMeal, MealIngredient mealIngredient, BigDecimal weight) {
        this.user = user;
        this.dietMeal = dietMeal;
        this.mealIngredient = mealIngredient;
        this.weight = weight;
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
}