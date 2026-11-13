package com.movies.movies_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice 
public class GlobalExceptionHandler {

    @ExceptionHandler(NoUnassignedMoviesException.class)
    public ProblemDetail handleNoMovies(NoUnassignedMoviesException ex) { 
        
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler (NoUserExistsException.class)
    public ProblemDetail handleNoUser(NoUserExistsException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    }
    @ExceptionHandler (UserMismatchException.class)
    public ProblemDetail handleUserMismatch(UserMismatchException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
    }
    @ExceptionHandler (RatingAlreadyExistsException.class)
    public ProblemDetail handleRatingDuplicate(RatingAlreadyExistsException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
    }
    @ExceptionHandler (MovieAssignmentDoesNotExistException.class)
    public ProblemDetail handleNoMovieAssignment(MovieAssignmentDoesNotExistException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    }
    @ExceptionHandler (NoUserExistsException.class)
    public ProblemDetail handleUserDoesNotExist(NoUserExistsException ex) {
       return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());

    }
    @ExceptionHandler(UserAlreadyRegisteredException.class)
    public ProblemDetail handleUserAlreadyRegistered(UserAlreadyRegisteredException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());


    }

    
}
