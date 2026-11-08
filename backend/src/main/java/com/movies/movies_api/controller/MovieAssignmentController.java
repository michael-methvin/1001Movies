package com.movies.movies_api.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.movies.movies_api.dto.MovieAssignmentResponse;
import com.movies.movies_api.service.MovieAssignmentService;

@RestController 
public class MovieAssignmentController {
    
    private final MovieAssignmentService movieAssignmentService;

    public MovieAssignmentController(MovieAssignmentService movieAssignmentService) {
        this.movieAssignmentService = movieAssignmentService;
    }


    @GetMapping("/assignments/daily")
    public MovieAssignmentResponse getDailyAssignmentForUser(@RequestParam Long userId) {
        return movieAssignmentService.getDailyAssignmentForUser(userId);
    }

}
