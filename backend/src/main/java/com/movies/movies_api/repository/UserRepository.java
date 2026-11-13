package com.movies.movies_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movies.movies_api.model.User;


public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByUsernameAndEmail(String username, String email);
}
