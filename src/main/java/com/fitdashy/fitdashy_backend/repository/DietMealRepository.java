package com.fitdashy.fitdashy_backend.repository;

import com.fitdashy.fitdashy_backend.model.DietMeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DietMealRepository extends JpaRepository<DietMeal, Integer> {
//    List<DietMeal> findByUserUserId(Integer userId); // Find all diet meals logged by a specific user
//    List<DietMeal> findByUserUserIdAndTimestampBetween(Integer userId, LocalDateTime start, LocalDateTime end); // Find diet meals for a user within a date range
//    Optional<DietMeal> findByDietMealIdAndUserUserId(Integer dietMealId, Integer userId); // Find a specific diet meal by ID and user ID
}