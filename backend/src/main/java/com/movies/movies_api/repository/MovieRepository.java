package com.movies.movies_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.movies.movies_api.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    

    boolean existsByTitleAndReleaseYear(String title, int releaseYear);

    
    @Query (value = "SELECT * FROM movies m WHERE NOT EXISTS (SELECT 1 FROM movie_assignments ma WHERE ma.movie_id = m.id AND ma.user_id = :userId) ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Optional<Movie> findRandomUnassignedMovieByUserId(@Param ("userId")Long userId);
}
