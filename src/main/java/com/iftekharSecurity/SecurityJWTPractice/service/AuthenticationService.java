package com.iftekharSecurity.SecurityJWTPractice.service;

import com.iftekharSecurity.SecurityJWTPractice.model.AuthenticationResponse;
import com.iftekharSecurity.SecurityJWTPractice.model.UserModel;
import com.iftekharSecurity.SecurityJWTPractice.repository.UserRepository;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(UserModel request){
        UserModel user = new UserModel();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setUsername(passwordEncoder.encode(request.getPassword()));

        user.setRole(user.getRole());
        user = repository.save(user);

        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token);

    }

    public AuthenticationResponse authenticationResponse(UserModel request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        UserModel userModel = repository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.generateToken(userModel);

        return new AuthenticationResponse(token);
    }

}
