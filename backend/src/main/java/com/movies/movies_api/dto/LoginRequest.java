package com.movies.movies_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    @NotBlank
    @Email 
    String email,
    
    @NotBlank 
    String password
    
) {
    
}
