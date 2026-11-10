package com.movies.movies_api.dto;

import com.movies.movies_api.model.Rating;

public record RatingResponse(

        Long ratingId,
        String review,
        Short score,
        Long movieAssignmentId) {
    
    public static RatingResponse from(Rating rating) {

        return new RatingResponse(rating.getId(),rating.getReview(),rating.getScore(),rating.getMovieAssignment().getId());
    }
}
    

