package com.movies.movies_api.exception;

public class MovieAssignmentDoesNotExistException extends RuntimeException {
    
    public MovieAssignmentDoesNotExistException(Long assignmentId) {
        super("MovieAssignmentId could not be found: " + assignmentId);
    
    }
}
