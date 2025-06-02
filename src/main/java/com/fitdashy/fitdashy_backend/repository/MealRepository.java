package com.fitdashy.fitdashy_backend.repository;

import com.fitdashy.fitdashy_backend.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MealRepository extends JpaRepository<Meal, Integer> {
//    List<Meal> findByUserUserId(Integer userId); // Find all meals created by a specific user
//    List<Meal> findByIsPrivateFalseOrUserUserId(Integer userId); // Find public meals or meals by a specific user
//    Optional<Meal> findByMealIdAndUserUserId(Integer mealId, Integer userId); // Find a specific meal by ID and user ID
}