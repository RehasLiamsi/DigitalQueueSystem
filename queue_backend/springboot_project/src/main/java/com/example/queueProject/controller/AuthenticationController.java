package com.example.queueProject.controller;

import com.example.queueProject.auth.AuthenticationResponse;
import com.example.queueProject.auth.UserRegisterOrAuthenticateRequest;
import com.example.queueProject.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody UserRegisterOrAuthenticateRequest request){
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody UserRegisterOrAuthenticateRequest request){
        return ResponseEntity.ok(service.authenticate(request));

    }

}
