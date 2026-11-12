package com.movies.movies_api.controller;


import org.springframework.web.bind.annotation.RestController;

import com.movies.movies_api.service.RatingService;

@RestController 
public class RatingController {
    
    private final RatingService ratingService;
    
    public RatingController(RatingService rs) {
        ratingService = rs;
    }





}
