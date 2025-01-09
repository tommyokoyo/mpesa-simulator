package com.okoyo.mpesasimulator.mpesasimulator.controllers;

import com.okoyo.mpesasimulator.mpesasimulator.dto.AuthenticationRequest;
import com.okoyo.mpesasimulator.mpesasimulator.dto.AuthenticationResponse;
import com.okoyo.mpesasimulator.mpesasimulator.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
@RequestMapping("/mpesa-simulator/v1/auth")
public class authentication {
    private final AuthenticationService authenticationService;

    @Autowired
    public authentication(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticateUser(@RequestBody AuthenticationRequest authenticationRequest) {
        if (authenticationRequest.getUsername() == null || authenticationRequest.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AuthenticationResponse("1", "Credentials can not be empty"));
        } else {
            AuthenticationResponse response = authenticationService.authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @GetMapping("/token")
    public ResponseEntity<?> authenticateToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationToken) {
        if (authorizationToken != null && authorizationToken.startsWith("Basic ")) {
            String base64Token = authorizationToken.substring("Basic ".length());

            byte[] tokens = Base64.getDecoder().decode(base64Token);
            String decodedToken = new String(tokens, StandardCharsets.UTF_8);

            String[] parts = decodedToken.split(":");
            if (parts.length == 2) {
                AuthenticationResponse response = authenticationService.authenticate(parts[0], parts[1]);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new AuthenticationResponse("1", "Invalid credentials"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AuthenticationResponse("1", "Invalid token"));
        }
    }
}
