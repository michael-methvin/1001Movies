package com.movies.movies_api.service;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.movies.movies_api.dto.AuthResponse;
import com.movies.movies_api.dto.LoginRequest;
import com.movies.movies_api.dto.RegisterRequest;
import com.movies.movies_api.dto.UserResponse;
import com.movies.movies_api.exception.InvalidCredentialsException;
import com.movies.movies_api.exception.UserAlreadyRegisteredException;
import com.movies.movies_api.model.User;
import com.movies.movies_api.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service 
public class AuthService {

    private final Clock clock;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepo;

    public AuthService(UserRepository ur, PasswordEncoder passwordEncoder, Clock clock,JwtEncoder jwtEncoder) {
        this.userRepo = ur;
        this.passwordEncoder = passwordEncoder;
        this.clock = clock;
        this.jwtEncoder = jwtEncoder;
    }

    @Transactional 
    public UserResponse registerUser(RegisterRequest request) {

        String email = request.email().trim().toLowerCase(Locale.ROOT);

        if(userRepo.existsByEmail(email) || userRepo.existsByUsername(request.username())) {
            throw new UserAlreadyRegisteredException(email,request.username());
        }
        
        User user = new User();
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        user.setUsername(request.username());

        return UserResponse.from(userRepo.save(user));
    }

    public AuthResponse logInUser(LoginRequest request) {

        String email = request.email().trim().toLowerCase(Locale.ROOT);

        User user = userRepo.findByEmail(email)
                            .filter(u -> passwordEncoder.matches(request.password(), u.getPasswordHash()))
                            .orElseThrow(() -> new InvalidCredentialsException());

    
        Instant now = Instant.now(clock);

        JwtClaimsSet claims = JwtClaimsSet.builder()
            .subject(String.valueOf(user.getId()))
            .issuedAt(now)
            .expiresAt(now.plus(1, ChronoUnit.HOURS))
            .build();
        
        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();

        String token = jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
        return new AuthResponse(token);







    }
        


    
}
