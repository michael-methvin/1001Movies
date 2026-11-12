package com.movies.movies_api.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.movies.movies_api.dto.MovieAssignmentResponse;
import com.movies.movies_api.dto.RatingRequest;
import com.movies.movies_api.dto.RatingResponse;
import com.movies.movies_api.exception.NoUnassignedMoviesException;
import com.movies.movies_api.exception.NoUserExistsException;
import com.movies.movies_api.exception.RatingAlreadyExistsException;
import com.movies.movies_api.exception.UserMismatchException;
import com.movies.movies_api.service.MovieAssignmentService;
import com.movies.movies_api.service.RatingService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.MediaType;

@WebMvcTest(MovieAssignmentController.class)
class MovieAssignmentControllerTest {

    @Autowired private MockMvc mockMvc;

    @MockitoBean private MovieAssignmentService movieAssignmentService;
    @MockitoBean private RatingService ratingService;
    
    @Test
    void noUserExists() throws Exception {

        // No User exists
        when(movieAssignmentService.getDailyAssignmentForUser(124L)).thenThrow(new NoUserExistsException(124L));
        mockMvc.perform(get("/api/assignments/daily").param("userId","124"))
        .andExpect(status().isNotFound());

    }
    @Test 
    void noMoviesRemaining() throws Exception {
        

        when(movieAssignmentService.getDailyAssignmentForUser(123L)).thenThrow(new NoUnassignedMoviesException(123L));
        
        mockMvc.perform(get("/api/assignments/daily").param("userId","123"))
        .andExpect(status().isConflict());
    }

    // Happy path - returns the MovieAssignmentResponse Record

    @Test
    void returnTodaysAssignment() throws Exception {
            MovieAssignmentResponse response = new MovieAssignmentResponse(2L,LocalDate.of(2026,8,11),3L,"Title in progress", 1970);

            when(movieAssignmentService.getDailyAssignmentForUser(123L)).thenReturn(response);

            mockMvc.perform(get("/api/assignments/daily").param("userId","123"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("Title in progress"))
            .andExpect(jsonPath("$.releaseYear").value(1970))
            .andExpect(jsonPath("$.assignmentDate").value("2026-08-11"));
    } 

    @Test
    void createRating() throws Exception {
        Short score = 10;
        
        RatingResponse happyResponse = new RatingResponse(123L, "Great", score, 124L);
        RatingRequest request = new RatingRequest(score, "Great");

        // Happy Path
        when(ratingService.createRating(124L, 125L, request)).thenReturn(happyResponse);

        mockMvc.perform(post("/api/assignments/124/rating")
                .param("userId","125")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"score\": 10, \"review\": \"Great\"}"))                
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.score").value(10));


    }
    @Test
    void duplicateRating() throws Exception {

        Short score = 10;
        
        RatingRequest request = new RatingRequest(score, "Great");

        when(ratingService.createRating(124L, 125L, request)).thenThrow(new RatingAlreadyExistsException(124L));

        mockMvc.perform(post("/api/assignments/124/rating")
            .param("userId", "125")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"score\": 10, \"review\": \"Great\"}"))
            .andExpect(status().isConflict());
    }

    @Test
    void invalidRating() throws Exception {


        mockMvc.perform(post("/api/assignments/124/rating")
            .param("userId", "125")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"score\": 11, \"review\": \"Great\"}"))
            .andExpect(status().isBadRequest());

        verify(ratingService, never()).createRating(any(), any(), any());
    }
    
    @Test
    void userIdMismatchRating() throws Exception {
        Short score = 10;
        
        RatingRequest request = new RatingRequest(score, "Great");

        when(ratingService.createRating(124L, 125L, request)).thenThrow(new UserMismatchException(123L,125L));

        mockMvc.perform(post("/api/assignments/124/rating")
            .param("userId", "125")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"score\": 10, \"review\": \"Great\"}"))
            .andExpect(status().isConflict());

    }
}
