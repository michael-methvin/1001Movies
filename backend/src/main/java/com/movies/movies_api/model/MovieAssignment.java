package com.movies.movies_api.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity 
@Table (name = "movie_assignments")
public class MovieAssignment {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @Column(name = "assignment_date", nullable = false)
    private LocalDate assignmentDate;

    public MovieAssignment() {

    }

    public Long getId() {
        return this.id;
    }
    public User getUser() {
        return this.user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Movie getMovie() {
        return this.movie;
    }
    public void setMovie(Movie movie) {
        this.movie = movie;
    }
    public LocalDate getAssignmentDate() {
        return this.assignmentDate;
    }
    public void setAssignmentDate(LocalDate assignmentDate) {
        this.assignmentDate = assignmentDate;
    }

}