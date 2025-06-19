package com.fitdashy.fitdashy_backend.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "meal_tags")
public class MealTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meal_tag_id")
    private Integer mealTagId;

    @Column(name = "tag_name", unique = true, nullable = false, length = 20)
    private String tagName;

    @Column(name = "tag_color", length = 8) // Stores hex color, e.g., "RRGGBB" or "AARRGGBB"
    private String tagColor = "808080"; // Default grey

    @Column(name = "usage_count")
    private Integer usageCount = 0; // Number of times this meal tag has been used

    @OneToMany(mappedBy = "mealTag", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MealHasTag> taggedMeals = new HashSet<>();

    // Constructors
    public MealTag() {
    }

    public MealTag(String tagName) {
        this.tagName = tagName;
    }

    public MealTag(String tagName, String tagColor) {
        this.tagName = tagName;
        this.tagColor = tagColor;
    }

    // Getters and Setters
    public Integer getMealTagId() {
        return mealTagId;
    }

    public void setMealTagId(Integer mealTagId) {
        this.mealTagId = mealTagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagColor() {
        return tagColor;
    }

    public void setTagColor(String tagColor) {
        this.tagColor = tagColor;
    }

    public Integer getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(Integer usageCount) {
        this.usageCount = usageCount;
    }

    public Set<MealHasTag> getTaggedMeals() {
        return taggedMeals;
    }

    public void setTaggedMeals(Set<MealHasTag> taggedMeals) {
        this.taggedMeals = taggedMeals;
    }

    @Override
    public String toString() {
        return "MealTag{" +
                "mealTagId=" + mealTagId +
                ", tagName='" + tagName + '\'' +
                ", tagColor='" + tagColor + '\'' +
                ", usageCount=" + usageCount +
                '}';
    }

    // You might want to add equals() and hashCode() if you plan to use MealTag objects in Sets or Maps
    // For simplicity, I'm omitting them, but consider generating them in IntelliJ.
    // @Override
    // public boolean equals(Object o) {
    //     if (this == o) return true;
    //     if (o == null || getClass() != o.getClass()) return false;
    //     MealTag mealTag = (MealTag) o;
    //     return Objects.equals(mealTagId, mealTag.mealTagId); // Or based on natural key like tagName
    // }

    // @Override
    // public int hashCode() {
    //     return Objects.hash(mealTagId); // Or based on natural key like tagName
    // }
}