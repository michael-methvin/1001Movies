package com.movies.movies_api.dto;


import com.movies.movies_api.model.User;

public record UserResponse(

    String username,
    String email

) {

    public static UserResponse from(User user) {

        return new UserResponse(user.getUsername(),user.getEmail());
    }
    
}
