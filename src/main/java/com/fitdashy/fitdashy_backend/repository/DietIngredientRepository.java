package com.fitdashy.fitdashy_backend.repository;

import com.fitdashy.fitdashy_backend.model.DietIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DietIngredientRepository extends JpaRepository<DietIngredient, Integer> {
}