package com.fitdashy.fitdashy_backend.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "diet_meals")
public class DietMeal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diet_meal_id")
    private Integer dietMealId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meal_id", nullable = false)
    private Meal meal;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "weight", nullable = false, precision = 6, scale = 1)
    private BigDecimal weight;

    // Relationship to DietIngredient: One DietMeal can have many DietIngredients
    @OneToMany(mappedBy = "dietMeal", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DietIngredient> dietIngredients = new HashSet<>();

    // Constructors
    public DietMeal() {}

    public DietMeal(User user, Meal meal, LocalDateTime timestamp, BigDecimal weight) {
        this.user = user;
        this.meal = meal;
        this.timestamp = timestamp;
        this.weight = weight;
    }

    // Helper methods for managing diet ingredients
    public void addDietIngredient(DietIngredient dietIngredient) {
        this.dietIngredients.add(dietIngredient);
        dietIngredient.setDietMeal(this);
    }

    public void removeDietIngredient(DietIngredient dietIngredient) {
        this.dietIngredients.remove(dietIngredient);
        dietIngredient.setDietMeal(null);
    }

    // Getters and Setters
    public Integer getDietMealId() {
        return dietMealId;
    }

    public void setDietMealId(Integer dietMealId) {
        this.dietMealId = dietMealId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public Set<DietIngredient> getDietIngredients() {
        return dietIngredients;
    }

    public void setDietIngredients(Set<DietIngredient> dietIngredients) {
        this.dietIngredients = dietIngredients;
    }
}
