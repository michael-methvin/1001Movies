package com.movies.movies_api.service;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movies.movies_api.dto.MovieAssignmentResponse;
import com.movies.movies_api.exception.NoUnassignedMoviesException;
import com.movies.movies_api.exception.NoUserExistsException;
import com.movies.movies_api.model.MovieAssignment;
import com.movies.movies_api.model.User;
import com.movies.movies_api.repository.MovieAssignmentRepository;
import com.movies.movies_api.repository.MovieRepository;
import com.movies.movies_api.repository.UserRepository;

@Service 
public class MovieAssignmentService {
    
    private final MovieAssignmentRepository movieAssignmentRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final Clock clock;

    

    public MovieAssignmentService(MovieAssignmentRepository movieAssignmentRepository, MovieRepository movieRepository, UserRepository userRepository, Clock clock) {
        this.movieAssignmentRepository = movieAssignmentRepository;
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
        this.clock = clock;
    }


    @Transactional 
    public MovieAssignmentResponse getDailyAssignmentForUser(Long userId) {
        // Check if the user exists
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NoUserExistsException(userId));
        

        // Get the current date and time
        LocalDate date = LocalDate.now(clock);

        Optional<MovieAssignment> assignment = movieAssignmentRepository.getByAssignmentDateAndUserId(date, user.getId());
        
        if(assignment.isPresent()) {
            return MovieAssignmentResponse.from(assignment.get());
        } 
        
            // Create a new assignment for the user on the given date
            MovieAssignment newAssignment = new MovieAssignment();
            newAssignment.setUser(user);
            newAssignment.setAssignmentDate(date);
            newAssignment.setMovie(movieRepository.findRandomUnassignedMovieByUserId(user.getId())
            .orElseThrow(() -> new NoUnassignedMoviesException(user.getId()))); 
            
            return MovieAssignmentResponse.from(movieAssignmentRepository.save(newAssignment));
        
       
    }
}
