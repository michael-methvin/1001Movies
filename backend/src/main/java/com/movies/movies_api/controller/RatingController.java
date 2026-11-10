package com.movies.movies_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.movies.movies_api.service.RatingService;

@RestController 
public class RatingController {
    
    private final RatingService ratingService;
    
    public RatingController(RatingService rs) {
        ratingService = rs;
    }





}
