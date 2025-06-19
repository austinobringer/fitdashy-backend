package com.fitdashy.fitdashy_backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "meals")
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meal_id")
    private Integer mealId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // Can be NULL, so Optional or handle nulls

    @Column(name = "meal_name", nullable = false)
    private String mealName;

    @Column(name = "usage_count")
    private Integer usageCount = 0;

    @Column(name = "is_private")
    private Boolean isPrivate = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // Relationship to MealIngredient: One Meal can have many MealIngredients
    // CascadeType.ALL ensures that if a Meal is saved/deleted, its MealIngredients are also managed.
    // orphanRemoval = true means if a MealIngredient is removed from the 'mealIngredients' set, it's deleted from the DB.
    @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MealIngredient> mealIngredients = new HashSet<>();

    @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MealHasTag> mealTags = new HashSet<>();

    // Constructors
    public Meal() {}

    public Meal(User user, String mealName, Boolean isPrivate) {
        this.user = user;
        this.mealName = mealName;
        this.isPrivate = isPrivate;
    }

    // Helper method to add meal ingredients
    public void addMealIngredient(MealIngredient mealIngredient) {
        this.mealIngredients.add(mealIngredient);
        mealIngredient.setMeal(this);
    }

    public void removeMealIngredient(MealIngredient mealIngredient) {
        this.mealIngredients.remove(mealIngredient);
        mealIngredient.setMeal(null);
    }

    public void addMealTag(MealTag tag) {
        MealHasTag mealHasTag = new MealHasTag(this, tag);
        this.mealTags.add(mealHasTag);
    }

    public void removeMealTag(MealTag tag) {
        this.mealTags.removeIf(mht -> mht.getMealTag().equals(tag));
    }

    // Getters and Setters
    public Integer getMealId() {
        return mealId;
    }

    public void setMealId(Integer mealId) {
        this.mealId = mealId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public Integer getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(Integer usageCount) {
        this.usageCount = usageCount;
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

    public Set<MealHasTag> getMealTags() {
        return mealTags;
    }

    public void setMealTags(Set<MealHasTag> mealTags) {
        this.mealTags = mealTags;
    }
}
