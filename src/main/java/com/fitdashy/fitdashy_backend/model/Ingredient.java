package com.fitdashy.fitdashy_backend.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "ingredients")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_id")
    private Integer ingredientId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // Can be NULL

    @Column(name = "ingredient_name", nullable = false)
    private String ingredientName;

    @Column(name = "usage_count")
    private Integer usageCount = 0;

    @Column(name = "weight", nullable = false, precision = 6, scale = 1)
    private BigDecimal weight; // Use BigDecimal for numeric types

    @Column(name = "calories", nullable = false, precision = 6, scale = 0)
    private Integer calories; // Use Integer for NUMERIC(6,0)

    @Column(name = "protein", precision = 6, scale = 1)
    private BigDecimal protein;

    @Column(name = "carbs", precision = 6, scale = 1)
    private BigDecimal carbs;

    @Column(name = "fat", precision = 6, scale = 1)
    private BigDecimal fat;

    @Column(name = "is_private")
    private Boolean isPrivate = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // Relationship to MealIngredient (optional, but good for navigation from ingredient side)
    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MealIngredient> mealIngredients;

    // Constructors
    public Ingredient() {}

    public Ingredient(User user, String ingredientName, BigDecimal weight, Integer calories, BigDecimal protein, BigDecimal carbs, BigDecimal fat, Boolean isPrivate) {
        this.user = user;
        this.ingredientName = ingredientName;
        this.weight = weight;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
        this.isPrivate = isPrivate;
    }

    // Getters and Setters
    public Integer getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Integer ingredientId) {
        this.ingredientId = ingredientId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public Integer getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(Integer usageCount) {
        this.usageCount = usageCount;
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

    public Boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Set<MealIngredient> getMealIngredients() {
        return mealIngredients;
    }

    public void setMealIngredients(Set<MealIngredient> mealIngredients) {
        this.mealIngredients = mealIngredients;
    }
}