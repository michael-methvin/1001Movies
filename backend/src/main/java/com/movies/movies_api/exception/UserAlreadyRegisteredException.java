package com.movies.movies_api.exception;

public class UserAlreadyRegisteredException extends RuntimeException {
    
    public UserAlreadyRegisteredException(String email, String username) {
        super("Username/Email already registered: " + email + " " + username);
    }
}
