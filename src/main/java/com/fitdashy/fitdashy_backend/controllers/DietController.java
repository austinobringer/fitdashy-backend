package com.fitdashy.fitdashy_backend.controllers;

import com.fitdashy.fitdashy_backend.payload.requests.MealSearchRequest;
import com.fitdashy.fitdashy_backend.payload.responses.MealResponse;
import com.fitdashy.fitdashy_backend.payload.responses.MessageResponse;
import com.fitdashy.fitdashy_backend.security.services.UserDetailsImpl;
import com.fitdashy.fitdashy_backend.services.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/diet")
public class DietController {

    private final MealService mealService;

    @Autowired
    public DietController(MealService mealService) {
        this.mealService = mealService;
    }

//    @GetMapping("/meals")
//    public ResponseEntity<?> getMeals(@ModelAttribute MealSearchRequest searchDto,
//                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        // Extract current user ID from Spring Security's UserDetails
//        // This ID is used by the service to handle public/private meal visibility.
//        Integer currentUserId = null;
//        if (userDetails != null) {
//            currentUserId = userDetails.getId();
//        }
//
//        List<MealResponse> meals = mealService.searchMeals(searchDto, currentUserId);
//
//        if (meals.isEmpty()) {
//            // Return 204 No Content if no meals match the criteria
//            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new MessageResponse("No meals were found."));
//        } else {
//            // Return 200 OK with the list of matching meals
//            return ResponseEntity
//                    .status(HttpStatus.OK)
//                    .body(meals);
//        }
//    }

    @GetMapping("/meals")
    public ResponseEntity<?> getMeals() {
        // Extract current user ID from Spring Security's UserDetails
        // This ID is used by the service to handle public/private meal visibility.
//        Integer currentUserId = null;
//        if (userDetails != null) {
//            currentUserId = userDetails.getId();
//        }

        List<MealResponse> meals = mealService.searchMeals();

        if (meals.isEmpty()) {
            // Return 204 No Content if no meals match the criteria
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new MessageResponse("No meals were found."));
        } else {
            // Return 200 OK with the list of matching meals
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(meals);
        }
    }
}