package com.fitdashy.fitdashy_backend.repository;

import com.fitdashy.fitdashy_backend.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
//    List<Ingredient> findByUserUserId(Integer userId); // Find all ingredients created by a specific user
//    List<Ingredient> findByIsPrivateFalseOrUserUserId(Integer userId); // Find public ingredients or ingredients by a specific user
//    Optional<Ingredient> findByIngredientIdAndUserUserId(Integer ingredientId, Integer userId); // Find a specific ingredient by ID and user ID
}