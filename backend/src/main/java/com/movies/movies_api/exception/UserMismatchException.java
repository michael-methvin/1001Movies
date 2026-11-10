package com.movies.movies_api.exception;

public class UserMismatchException extends RuntimeException {
    public UserMismatchException(Long suppliedId,Long recordedId) {
        super("User Id Mismatch: SuppliedId: " + suppliedId);
    }
}
