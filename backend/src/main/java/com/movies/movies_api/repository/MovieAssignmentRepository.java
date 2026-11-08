package com.movies.movies_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movies.movies_api.model.MovieAssignment;
import java.time.LocalDate;

public interface MovieAssignmentRepository extends JpaRepository<MovieAssignment, Long> {
    
    Optional<MovieAssignment> getByAssignmentDateAndUserId(LocalDate date, Long userId);
    boolean existsByAssignmentDateAndUserId(LocalDate date, Long userId);
}
