package com.fitdashy.fitdashy_backend.repository;

import com.fitdashy.fitdashy_backend.model.DietMeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DietMealRepository extends JpaRepository<DietMeal, Integer> {
}