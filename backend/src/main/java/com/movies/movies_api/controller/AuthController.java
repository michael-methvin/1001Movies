package com.movies.movies_api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.movies.movies_api.dto.AuthResponse;
import com.movies.movies_api.dto.LoginRequest;
import com.movies.movies_api.dto.RegisterRequest;
import com.movies.movies_api.dto.UserResponse;
import com.movies.movies_api.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    private final AuthService authService;
    
    public AuthController(AuthService service) {
        this.authService = service;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse registerUser(@Valid @RequestBody RegisterRequest request) {
        return authService.registerUser(request);
    }



    @PostMapping("/login")
    public AuthResponse loginUser(@Valid @RequestBody LoginRequest request) {

        return authService.logInUser(request);

        


    }
    
    

}
