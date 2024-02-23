package com.iftekharSecurity.SecurityJWTPractice.controller;

import com.iftekharSecurity.SecurityJWTPractice.model.AuthenticationResponse;
import com.iftekharSecurity.SecurityJWTPractice.model.UserModel;
import com.iftekharSecurity.SecurityJWTPractice.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody UserModel userModel){
        return ResponseEntity.ok(authenticationService.register(userModel));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody UserModel userModel){
        return ResponseEntity.ok(authenticationService.authenticationResponse(userModel));
    }

}
