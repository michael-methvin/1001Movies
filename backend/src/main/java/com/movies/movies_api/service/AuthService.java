package com.movies.movies_api.service;

import org.springframework.stereotype.Service;

import com.movies.movies_api.dto.UserResponse;
import com.movies.movies_api.exception.UserAlreadyRegistered;
import com.movies.movies_api.model.User;
import com.movies.movies_api.repository.UserRepository;

@Service 
public class AuthService {

    private final UserRepository userRepo;

    public AuthService(UserRepository ur) {
        this.userRepo = ur;
    }

    public UserResponse registerUser(User user) {
        if(userRepo.existsByEmail(user.getEmail()) || userRepo.existsByUsername(user.getUsername())) {
            throw new UserAlreadyRegistered(user.getEmail(),user.getUsername());
        }

        return UserResponse.from(userRepo.save(user));


    }
    
}
