package com.movies.movies_api.exception;

public class UserDoesNotExistException extends RuntimeException {
    
    public UserDoesNotExistException(String email) {
        super("No user found with email: " + email);
    }
    
}
