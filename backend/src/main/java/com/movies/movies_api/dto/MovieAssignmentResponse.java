package com.movies.movies_api.dto;

import java.time.LocalDate;

import com.movies.movies_api.model.Movie;
import com.movies.movies_api.model.MovieAssignment;

public record MovieAssignmentResponse(
        Long assignmentId,
        LocalDate assignmentDate,
        Long movieId,
        String title,
        Integer releaseYear) {
    
    public static MovieAssignmentResponse from(MovieAssignment assignment) {
        Movie movie = assignment.getMovie();
        return new MovieAssignmentResponse(
                assignment.getId(),
                assignment.getAssignmentDate(),
                movie.getId(),
                movie.getTitle(),
                movie.getReleaseYear()
        );
    }
}
    

