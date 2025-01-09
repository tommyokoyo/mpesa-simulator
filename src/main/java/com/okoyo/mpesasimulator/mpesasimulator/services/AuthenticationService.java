package com.okoyo.mpesasimulator.mpesasimulator.services;

import com.okoyo.mpesasimulator.mpesasimulator.components.JWTUtil;
import com.okoyo.mpesasimulator.mpesasimulator.dto.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final JWTUtil jwtUtil;

    @Autowired
    public AuthenticationService(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    public AuthenticationResponse authenticate(String username, String password) {
        if (!username.isBlank() || !password.isBlank()) {
            if (username.equals("admin") && password.equals("admin")) {
                return new AuthenticationResponse("0", jwtUtil.generateToken(username));
            } else {
                return new AuthenticationResponse("1", "Invalid credentials");
            }
        } else {
            return new AuthenticationResponse("1", "Credentials can not be empty");
        }
    }
}
