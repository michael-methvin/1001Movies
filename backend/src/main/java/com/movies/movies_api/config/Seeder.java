package com.movies.movies_api.config;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.movies.movies_api.model.Movie;
import com.movies.movies_api.repository.MovieRepository;


@Component 
public class Seeder implements CommandLineRunner {

    private final boolean seedEnabled;
    private final Resource seedFile;
    private final MovieRepository movieRepository;
    private static final Logger log = LoggerFactory.getLogger(Seeder.class);

    public Seeder(@Value("${app.seed.enabled}") boolean seedEnabled, @Value("${app.seed.file}") Resource seedFile, MovieRepository movieRepository) {
        this.seedEnabled = seedEnabled;
        this.seedFile = seedFile;
        this.movieRepository = movieRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Seed data logic here
        if (!seedEnabled || !seedFile.exists()) {
             if(!seedEnabled) {
                log.warn("Seeding is Disabled.");
            }
            if(!seedFile.exists()) {
                log.warn("Seed file does not exist: " + seedFile.getDescription());
            }
        }
        else { 
            log.info("Seeding is enabled. Using file: " + seedFile.getFilename());
            // Implement the logic to read from the seedFile and populate the database
            try(InputStream in = seedFile.getInputStream();
                Reader reader = new InputStreamReader(in, StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(reader)) {

                // Read the CSV file and populate the database
                CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                        .setHeader()
                        .setSkipHeaderRecord(true)
                        .setTrim(true)
                        .get();
                
                List<Movie> movies = new ArrayList<>();

                try (CSVParser parser = csvFormat.parse(bufferedReader)) {
                   for(CSVRecord record : parser) {
                      Movie movie = new Movie();
                      movie.setReleaseYear(Integer.parseInt(record.get("Year")));
                      movie.setTitle(record.get("Title"));
                      movies.add(movie);
                    }
                    log.info("Parsed " + movies.size() + " movies from the seed file.");
                }
                
            } 
            catch (IOException e) {
                log.error("Error reading seed file: " + e.getMessage(), e);
            }
        }
    }
    
}
