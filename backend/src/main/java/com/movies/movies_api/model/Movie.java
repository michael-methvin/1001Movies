package com.movies.movies_api.model;




import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table(name = "movies")
public class Movie {
    
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Long id;

    private String title;
    private Integer releaseYear;

    public Movie() {

    }

    public Long getId() {
        return this.id;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Integer getReleaseYear() {
        return this.releaseYear;
    }
    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

}
