package com.movies.movies_api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movies.movies_api.dto.UserRequest;
import com.movies.movies_api.dto.UserResponse;
import com.movies.movies_api.model.User;
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
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody UserRequest request) {

        User user = new User();
        user.setEmail(request.email());
        user.setUsername(request.username());
        

        UserResponse userResponse = authService.registerUser(user);

        if(userResponse != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);

        }
        else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(userResponse);
        }

    }
    

}
