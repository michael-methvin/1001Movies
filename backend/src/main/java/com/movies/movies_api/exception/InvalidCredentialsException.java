package com.movies.movies_api.exception;

public class InvalidCredentialsException extends RuntimeException{
    
    public InvalidCredentialsException() {
        super("Invalid Credentials Supplied");
    }
}
