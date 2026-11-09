package com.movies.movies_api.controller;

import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.movies.movies_api.dto.MovieAssignmentResponse;
import com.movies.movies_api.exception.NoUnassignedMoviesException;
import com.movies.movies_api.exception.NoUserExistsException;
import com.movies.movies_api.service.MovieAssignmentService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MovieAssignmentController.class)
class MovieAssignmentControllerTest {

    @Autowired private MockMvc mockMvc;

    @MockitoBean private MovieAssignmentService movieAssignmentService;
    
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
}
