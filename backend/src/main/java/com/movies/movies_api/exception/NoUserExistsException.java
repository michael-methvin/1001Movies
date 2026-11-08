package com.movies.movies_api.exception;

public class NoUserExistsException extends RuntimeException {
    
    public NoUserExistsException(Long userId) {
        super("User not found with id: " + userId);
    }
    
}
