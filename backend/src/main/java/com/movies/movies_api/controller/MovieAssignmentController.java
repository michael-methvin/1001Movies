package com.movies.movies_api.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.movies.movies_api.dto.MovieAssignmentResponse;
import com.movies.movies_api.dto.RatingRequest;
import com.movies.movies_api.dto.RatingResponse;
import com.movies.movies_api.service.MovieAssignmentService;
import com.movies.movies_api.service.RatingService;

import jakarta.validation.Valid;

@RestController 
@RequestMapping("/api/assignments")
public class MovieAssignmentController {
    
    private final MovieAssignmentService movieAssignmentService;
    private final RatingService ratingService;

    public MovieAssignmentController(MovieAssignmentService movieAssignmentService, RatingService ratingService) {
        this.movieAssignmentService = movieAssignmentService;
        this.ratingService = ratingService;
    }


    @GetMapping("/daily")
    public MovieAssignmentResponse getDailyAssignmentForUser(@RequestParam Long userId) {
        return movieAssignmentService.getDailyAssignmentForUser(userId);
    }


    @PostMapping("/{assignmentId}/rating")
    public ResponseEntity<RatingResponse> createRating(
        @PathVariable Long assignmentId,
        @RequestParam Long userId,
        @Valid @RequestBody RatingRequest request


    ){
        ratingService.

    }

}
