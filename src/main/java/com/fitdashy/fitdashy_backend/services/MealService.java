package com.fitdashy.fitdashy_backend.services;

import com.fitdashy.fitdashy_backend.payload.requests.MealSearchRequest;
import com.fitdashy.fitdashy_backend.payload.responses.MealResponse;
import com.fitdashy.fitdashy_backend.payload.responses.MealIngredientResponse;
import com.fitdashy.fitdashy_backend.model.Meal;
import com.fitdashy.fitdashy_backend.model.MealIngredient;
import com.fitdashy.fitdashy_backend.model.User;
import com.fitdashy.fitdashy_backend.repository.MealRepository;
import com.fitdashy.fitdashy_backend.repository.UserRepository;

import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
public class MealService {

    private final MealRepository mealRepository;
    private final UserRepository userRepository; // To fetch user details for response

    @Autowired
    public MealService(MealRepository mealRepository, UserRepository userRepository) {
        this.mealRepository = mealRepository;
        this.userRepository = userRepository;
    }

    /**
     * Searches for meals based on various criteria.
     * Applies filters for meal name, ingredient IDs, and visibility (public/private).
     * Performs in-memory filtering for aggregate nutritional values.
     *
     * @param searchDto       The DTO containing search criteria.
     * @param currentUserId   The ID of the currently authenticated user (can be null for unauthenticated requests).
     * @return A list of MealResponseDto objects matching the criteria.
     */
    @Transactional(readOnly = true)
    public List<MealResponse> searchMeals(MealSearchRequest searchDto, Integer currentUserId) {
        // Build the dynamic query specification based on non-aggregate criteria
        Specification<Meal> spec = buildMealSpecification(searchDto, currentUserId);

        // Fetch meals from the repository based on the specification
        List<Meal> meals = mealRepository.findAll(spec);

        // Convert entities to DTOs and apply in-memory filtering for aggregate nutritional values
        List<MealResponse> filteredMeals = meals.stream()
                .map(meal -> {
                    // Calculate total nutritional values for the meal
                    BigDecimal totalCalories = BigDecimal.ZERO;
                    BigDecimal totalProtein = BigDecimal.ZERO;
                    BigDecimal totalCarbs = BigDecimal.ZERO;
                    BigDecimal totalFat = BigDecimal.ZERO;

                    List<MealIngredientResponseDto> ingredientDtos = new ArrayList<>();

                    // Ensure mealIngredients collection is initialized (triggers lazy loading)
                    // This is crucial for accessing the collection within the transaction.
                    if (meal.getMealIngredients() != null) {
                        for (MealIngredient mi : meal.getMealIngredients()) {
                            // Aggregate nutrients, handling potential nulls
                            totalCalories = totalCalories.add(mi.getCalories() != null ? BigDecimal.valueOf(mi.getCalories()) : BigDecimal.ZERO);
                            totalProtein = totalProtein.add(mi.getProtein() != null ? mi.getProtein() : BigDecimal.ZERO);
                            totalCarbs = totalCarbs.add(mi.getCarbs() != null ? mi.getCarbs() : BigDecimal.ZERO);
                            totalFat = totalFat.add(mi.getFat() != null ? mi.getFat() : BigDecimal.ZERO);

                            // Map MealIngredient entity to MealIngredientResponseDto
                            MealIngredientResponseDto miDto = new MealIngredientResponseDto();
                            miDto.setIngredientId(mi.getIngredient() != null ? mi.getIngredient().getIngredientId() : null);
                            miDto.setIngredientName(mi.getIngredient() != null ? mi.getIngredient().getIngredientName() : null);
                            miDto.setWeight(mi.getWeight());
                            miDto.setCalories(mi.getCalories() != null ? mi.getCalories().doubleValue() : 0.0); // Convert to Double for DTO
                            miDto.setProtein(mi.getProtein() != null ? mi.getProtein().doubleValue() : 0.0);
                            miDto.setCarbs(mi.getCarbs() != null ? mi.getCarbs().doubleValue() : 0.0);
                            miDto.setFat(mi.getFat() != null ? mi.getFat().doubleValue() : 0.0);
                            ingredientDtos.add(miDto);
                        }
                    }

                    // Apply in-memory nutritional filters
                    if (searchDto.getMinCalories() != null && totalCalories.compareTo(BigDecimal.valueOf(searchDto.getMinCalories())) < 0) return null;
                    if (searchDto.getMaxCalories() != null && totalCalories.compareTo(BigDecimal.valueOf(searchDto.getMaxCalories())) > 0) return null;
                    if (searchDto.getMinProtein() != null && totalProtein.compareTo(BigDecimal.valueOf(searchDto.getMinProtein())) < 0) return null;
                    if (searchDto.getMaxProtein() != null && totalProtein.compareTo(BigDecimal.valueOf(searchDto.getMaxProtein())) > 0) return null;
                    if (searchDto.getMinCarbs() != null && totalCarbs.compareTo(BigDecimal.valueOf(searchDto.getMinCarbs())) < 0) return null;
                    if (searchDto.getMaxCarbs() != null && totalCarbs.compareTo(BigDecimal.valueOf(searchDto.getMaxCarbs())) > 0) return null;
                    if (searchDto.getMinFat() != null && totalFat.compareTo(BigDecimal.valueOf(searchDto.getMinFat())) < 0) return null;
                    if (searchDto.getMaxFat() != null && totalFat.compareTo(BigDecimal.valueOf(searchDto.getMaxFat())) > 0) return null;

                    // Map Meal entity to MealResponseDto
                    MealResponseDto mealDto = new MealResponseDto();
                    mealDto.setMealId(meal.getMealId());
                    mealDto.setName(meal.getMealName()); // Mapping mealName to 'name' in DTO
                    mealDto.setAmount(meal.getUsageCount()); // Mapping usage_count to 'amount' in DTO, or define what 'amount' means

                    // Set calculated total nutritional values
                    mealDto.setTotalCalories(totalCalories.doubleValue());
                    mealDto.setTotalProtein(totalProtein.doubleValue());
                    mealDto.setTotalCarbs(totalCarbs.doubleValue());
                    mealDto.setTotalFat(totalFat.doubleValue());

                    mealDto.setIngredients(ingredientDtos); // Set the list of ingredient DTOs

                    // Populate user name if the meal has an associated user
                    Optional.ofNullable(meal.getUser())
                            .map(User::getUsername)
                            .ifPresent(mealDto::setUserName);

                    return mealDto;
                })
                .filter(java.util.Objects::nonNull) // Remove meals that were filtered out by nutrition criteria
                .collect(Collectors.toList());

        return filteredMeals;
    }

    /**
     * Builds the JPA Specification for dynamic query filtering based on MealSearchRequestDto.
     * This handles filters that can be directly applied in the database (name, privacy, ingredientIds).
     *
     * @param criteria      The search criteria DTO.
     * @param currentUserId The ID of the current user for privacy filtering.
     * @return A Specification object for the Meal entity.
     */
    private Specification<Meal> buildMealSpecification(MealSearchRequest criteria, Integer currentUserId) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 1. Filter by meal name (case-insensitive LIKE)
            if (criteria.getName() != null && !criteria.getName().trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("mealName")), "%" + criteria.getName().toLowerCase() + "%"));
            }

            // 2. Filter by privacy: Public meals OR meals created by the current user
            if (currentUserId != null) {
                Predicate userSpecificMeals = criteriaBuilder.equal(root.get("user").get("userId"), currentUserId);
                Predicate publicMeals = criteriaBuilder.isFalse(root.get("isPrivate"));
                predicates.add(criteriaBuilder.or(userSpecificMeals, publicMeals));
            } else {
                // If no user is authenticated, only show public meals
                predicates.add(criteriaBuilder.isFalse(root.get("isPrivate")));
            }

            // 3. Filter by ingredient IDs (find meals containing ANY of these ingredients)
            if (criteria.getIngredientIds() != null && !criteria.getIngredientIds().isEmpty()) {
                // Join to mealIngredients and then to Ingredient
                jakarta.persistence.criteria.Join<Meal, MealIngredient> mealIngredientsJoin = root.join("mealIngredients");
                jakarta.persistence.criteria.Join<MealIngredient, com.fitdashy.fitdashy_backend.model.Ingredient> ingredientJoin = mealIngredientsJoin.join("ingredient");
                predicates.add(ingredientJoin.get("ingredientId").in(criteria.getIngredientIds()));

                // Ensure distinct meals are returned if multiple ingredients match the same meal
                query.distinct(true);
            }

            // 'amount' (from DTO) can be mapped to 'usageCount' in Meal model if that's its meaning.
            // If so, add a predicate here:
            // if (criteria.getAmount() != null) {
            //     predicates.add(criteriaBuilder.equal(root.get("usageCount"), criteria.getAmount()));
            // }


            // Note: Nutritional filters (min/max calories, carbs, protein, fat) are handled
            // in-memory *after* fetching from the database, because they require aggregation
            // (SUM) which is difficult to apply directly within a JpaSpecificationExecutor
            // on the main query for performance. For high-performance needs, consider
            // denormalizing total nutrients onto the Meal entity or using a custom @Query
            // with GROUP BY and HAVING clauses.

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    // You can add other meal-related service methods here as needed, e.g.,
    // @Transactional
    // public MealResponseDto createMeal(MealCreateRequestDto createDto, Integer userId) {
    //    // ... logic to create and save meal entity, then convert to DTO ...
    // }
}