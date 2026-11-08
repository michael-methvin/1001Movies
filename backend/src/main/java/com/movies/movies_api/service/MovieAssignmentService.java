package com.movies.movies_api.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movies.movies_api.exception.NoUnassignedMoviesException;
import com.movies.movies_api.model.MovieAssignment;
import com.movies.movies_api.model.User;
import com.movies.movies_api.repository.MovieAssignmentRepository;
import com.movies.movies_api.repository.MovieRepository;

@Service 
public class MovieAssignmentService {
    
    private final MovieAssignmentRepository movieAssignmentRepository;
    private final MovieRepository movieRepository;

    public MovieAssignmentService(MovieAssignmentRepository movieAssignmentRepository, MovieRepository movieRepository) {
        this.movieAssignmentRepository = movieAssignmentRepository;
        this.movieRepository = movieRepository;
    }


    @Transactional 
    public MovieAssignment getDailyAssignmentForUser(User user, LocalDate date) {
        
        Optional<MovieAssignment> assignment = movieAssignmentRepository.getByAssignmentDateAndUserId(date, user.getId());
        
        if(assignment.isPresent()) {
            return assignment.get();
        } 
        
            // Create a new assignment for the user on the given date
            MovieAssignment newAssignment = new MovieAssignment();
            newAssignment.setUser(user);
            newAssignment.setAssignmentDate(date);
            newAssignment.setMovie(movieRepository.findRandomUnassignedMovieByUserId(user.getId())
            .orElseThrow(() -> new NoUnassignedMoviesException(user.getId()))); 
            
            return movieAssignmentRepository.save(newAssignment);
        
       
        }
}
