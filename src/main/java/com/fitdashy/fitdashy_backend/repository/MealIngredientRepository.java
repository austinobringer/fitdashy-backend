package com.fitdashy.fitdashy_backend.repository;

import com.fitdashy.fitdashy_backend.model.MealIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealIngredientRepository extends JpaRepository<MealIngredient, Integer> {
}