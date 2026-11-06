package com.movies.movies_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.movies.movies_api.model.Movie;
import com.movies.movies_api.repository.MovieRepository;

@Service 
public class MovieService {
    
    private final MovieRepository movieRepository;
    
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }


    public List<Movie> findAll() {
        return movieRepository.findAll();
    }
    
}
