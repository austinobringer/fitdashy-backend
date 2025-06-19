package com.fitdashy.fitdashy_backend.model;

import jakarta.persistence.*;

import java.util.Objects; // For equals and hashCode

@Entity
@Table(name = "meal_has_tags")
public class MealHasTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meal_has_tag_id")
    private Integer mealHasTagId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meal_id", nullable = false)
    private Meal meal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meal_tag_id", nullable = false)
    private MealTag mealTag;

    // Constructors
    public MealHasTag() {
    }

    public MealHasTag(Meal meal, MealTag mealTag) {
        this.meal = meal;
        this.mealTag = mealTag;
    }

    // Getters and Setters
    public Integer getMealHasTagId() {
        return mealHasTagId;
    }

    public void setMealHasTagId(Integer mealHasTagId) {
        this.mealHasTagId = mealHasTagId;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public MealTag getMealTag() {
        return mealTag;
    }

    public void setMealTag(MealTag mealTag) {
        this.mealTag = mealTag;
    }

    // Recommended: Override equals and hashCode for proper Set behavior
    // This is crucial for managing collections of this entity,
    // especially if you rely on its uniqueness within a Set.
    // Consider using a business key (meal and mealTag combination) for equals/hashCode
    // if mealHasTagId is only an auto-generated primary key and not a true identifier for uniqueness checks.
    // For many-to-many join entities, it's common to use the composite of the two foreign keys.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MealHasTag that = (MealHasTag) o;
        return Objects.equals(meal, that.meal) &&
                Objects.equals(mealTag, that.mealTag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(meal, mealTag);
    }

    @Override
    public String toString() {
        return "MealHasTag{" +
                "mealHasTagId=" + mealHasTagId +
                ", mealId=" + (meal != null ? meal.getMealId() : "null") +
                ", mealTagId=" + (mealTag != null ? mealTag.getMealTagId() : "null") +
                '}';
    }
}