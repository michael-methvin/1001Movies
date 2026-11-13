package com.movies.movies_api.exception;

public class UserAlreadyRegistered extends RuntimeException {
    
    public UserAlreadyRegistered(String email, String username) {
        super("Username/Email already registered: " + email + " " + username);
    }
}
