package com.movies.movies_api.service;

import java.time.Clock;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movies.movies_api.dto.RatingRequest;
import com.movies.movies_api.dto.RatingResponse;
import com.movies.movies_api.exception.MovieAssignmentDoesNotExistException;
import com.movies.movies_api.exception.RatingAlreadyExistsException;
import com.movies.movies_api.exception.UserMismatchException;
import com.movies.movies_api.model.MovieAssignment;
import com.movies.movies_api.model.Rating;
import com.movies.movies_api.repository.MovieAssignmentRepository;
import com.movies.movies_api.repository.RatingRepository;


@Service 
public class RatingService {

    private final Clock clock;
    private final RatingRepository ratingRepository;
    private final MovieAssignmentRepository movieAssignmentRepository;

    public RatingService(RatingRepository rr, MovieAssignmentRepository mva, Clock clock) {
        this.ratingRepository = rr;
        this.movieAssignmentRepository = mva;
        this.clock = clock;
    }

    @Transactional 
    public RatingResponse createRating(Long assignmentId,Long userId, RatingRequest request ) {
        
        MovieAssignment movieAssignment = movieAssignmentRepository.findById(assignmentId).orElseThrow(() -> new MovieAssignmentDoesNotExistException(assignmentId));


        if(!userId.equals(movieAssignment.getUser().getId())) {
            throw new UserMismatchException(userId, movieAssignment.getUser().getId());
        }

        if(ratingRepository.existsByMovieAssignmentId(assignmentId)) {
            throw new RatingAlreadyExistsException(assignmentId);
        }
    
        Short score = request.score();
        String review = request.review();
        Rating rating = new Rating();
        rating.setMovieAssignment(movieAssignment);
        rating.setReview(review);
        rating.setScore(score);
        
        Rating saved = ratingRepository.save(rating);
        return RatingResponse.from(saved);
    }

}
