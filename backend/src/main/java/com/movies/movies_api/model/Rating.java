package com.movies.movies_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ratings")
public class Rating {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="movie_assignment_id",nullable = false)
    private MovieAssignment movieAssignment;
    private String review;
    private Short score;

    public Rating() {

    }

    public Long getId() {
        return this.id; 
    }
    public MovieAssignment getMovieAssignment() {
        return this.movieAssignment;
    }

    public void setMovieAssignment(MovieAssignment movieAssignment) {
        this.movieAssignment = movieAssignment;
    }

    public String getReview() {
        return this.review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Short getScore() {
        return this.score;
    }

    public void setScore(Short score) {
        this.score = score;
    }

}
