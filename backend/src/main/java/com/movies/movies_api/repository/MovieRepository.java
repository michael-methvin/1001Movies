package com.movies.movies_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movies.movies_api.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    
}
