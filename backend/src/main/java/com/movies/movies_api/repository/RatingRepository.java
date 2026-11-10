package com.movies.movies_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movies.movies_api.model.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    
    boolean existsByMovieAssignmentId(Long movieAssignmentId);
}
