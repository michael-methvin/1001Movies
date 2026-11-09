package com.movies.movies_api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.movies.movies_api.dto.MovieAssignmentResponse;
import com.movies.movies_api.exception.NoUnassignedMoviesException;
import com.movies.movies_api.exception.NoUserExistsException;
import com.movies.movies_api.model.Movie;
import com.movies.movies_api.model.MovieAssignment;
import com.movies.movies_api.model.User;
import com.movies.movies_api.repository.MovieAssignmentRepository;
import com.movies.movies_api.repository.MovieRepository;
import com.movies.movies_api.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class MovieAssignmentServiceTest {


    
    @Mock private MovieAssignmentRepository movieAssignmentRepository;
    @Mock private MovieRepository movieRepository;
    @Mock private UserRepository userRepository;
    
    private Clock clock;
    MovieAssignmentService service;

    @BeforeEach void setUp() {
        clock = Clock.fixed(Instant.parse("2026-11-08T12:00:00Z"), ZoneOffset.UTC);    
        service = new MovieAssignmentService(movieAssignmentRepository, movieRepository, userRepository, clock);
    
    }

    @Test
    void returnsExistingAssignment() {

    
    User user = new User();
    ReflectionTestUtils.setField(user,"id", 123L);
    when(userRepository.findById(123L)).thenReturn(Optional.of(user));

    

    Movie movie = new Movie();
    movie.setReleaseYear(1970);
    movie.setTitle("Testing the limits");

    MovieAssignment movieAssignment = new MovieAssignment();
    movieAssignment.setMovie(movie);
    movieAssignment.setUser(user);
    movieAssignment.setAssignmentDate(LocalDate.of(2026,11,8));
    when(movieAssignmentRepository.getByAssignmentDateAndUserId(LocalDate.of(2026,11,8), 123L)).thenReturn(Optional.of(movieAssignment));

    MovieAssignmentResponse response = service.getDailyAssignmentForUser(user.getId());

    assertEquals(movie.getTitle(), response.title());
    assertEquals(movie.getReleaseYear(), response.releaseYear());
    verify(movieAssignmentRepository, never()).save(any());

    }

    @Test
    void createsNewAssignment() {
        User user = new User();
        ReflectionTestUtils.setField(user,"id", 123L);
        when(userRepository.findById(123L)).thenReturn(Optional.of(user));
    
        Movie movie = new Movie();
        movie.setReleaseYear(1970);
        movie.setTitle("Testing the limits");


        when(movieAssignmentRepository.getByAssignmentDateAndUserId(LocalDate.of(2026,11,8), 123L)).thenReturn(Optional.empty());

        when(movieRepository.findRandomUnassignedMovieByUserId(123L)).thenReturn(Optional.of(movie));

        when(movieAssignmentRepository.save(any(MovieAssignment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        MovieAssignmentResponse response;
        response = service.getDailyAssignmentForUser(123L);
        assertEquals(movie.getTitle(), response.title());
        assertEquals(movie.getReleaseYear(), response.releaseYear());

        ArgumentCaptor<MovieAssignment> captor = ArgumentCaptor.forClass(MovieAssignment.class);
        verify(movieAssignmentRepository).save(captor.capture());
        MovieAssignment saved = captor.getValue();
        
        assertEquals(movie, saved.getMovie());
        assertEquals(user, saved.getUser());
        assertEquals(LocalDate.of(2026,11,8), saved.getAssignmentDate());

        
    } 

    @Test
    void noMoviesLeft() {
        User user = new User();
        ReflectionTestUtils.setField(user,"id", 123L);
        when(userRepository.findById(123L)).thenReturn(Optional.of(user));

        when(movieAssignmentRepository.getByAssignmentDateAndUserId(LocalDate.of(2026,11,8), 123L)).thenReturn(Optional.empty());
        
        when(movieRepository.findRandomUnassignedMovieByUserId(123L)).thenReturn(Optional.empty());

        assertThrows(NoUnassignedMoviesException.class, 
            () -> service.getDailyAssignmentForUser(123L));
        verify(movieAssignmentRepository, never()).save(any());

    }

    @Test
    void userNotFound() {


        when(userRepository.findById(123L)).thenReturn(Optional.empty());

        assertThrows(NoUserExistsException.class, 
            () -> service.getDailyAssignmentForUser(123L));
        verifyNoInteractions(movieAssignmentRepository,movieRepository);

    }


}