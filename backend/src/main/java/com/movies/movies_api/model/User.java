package com.movies.movies_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table(name = "users")
public class User {
    
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private  Long id;
    @Column (unique = true, nullable = false)
    private  String email;
    @Column (nullable = false)
    private  String passwordHash;
    @Column (unique = true, nullable = false)
    private  String username;

    public User() {

    }

    public Long getId() {
        return this.id;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPasswordHash() {
        return this.passwordHash;
    }
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

}
