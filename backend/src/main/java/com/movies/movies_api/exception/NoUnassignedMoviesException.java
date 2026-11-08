package com.movies.movies_api.exception;

public class NoUnassignedMoviesException extends RuntimeException {
    
    public NoUnassignedMoviesException(Long userId) {
        super("No unassigned movies available for user with ID: " + userId);
    }
    
}
