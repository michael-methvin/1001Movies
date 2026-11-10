package com.movies.movies_api.exception;

public class RatingAlreadyExistsException extends RuntimeException {
        
    public RatingAlreadyExistsException(Long movieAssignmentId) {
        super("Rating already exists with id: " + movieAssignmentId);
    }
}
