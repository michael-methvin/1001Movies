package com.movies.movies_api.exception;

public class UserDoesNotExistException extends RuntimeException {
    
    public UserDoesNotExistException(String username, String email) {
        super("No user found with username and email: " + username + " " + email);
    }
    
}
