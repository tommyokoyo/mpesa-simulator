package com.okoyo.mpesasimulator.mpesasimulator.services;

import com.okoyo.mpesasimulator.mpesasimulator.components.Generator;
import com.okoyo.mpesasimulator.mpesasimulator.components.JWTUtil;
import com.okoyo.mpesasimulator.mpesasimulator.dto.AuthenticationResponse;
import com.okoyo.mpesasimulator.mpesasimulator.dto.UserRequest;
import com.okoyo.mpesasimulator.mpesasimulator.entities.User;
import com.okoyo.mpesasimulator.mpesasimulator.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthenticationService {
    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
    private final Generator generator;

    @Autowired
    public AuthenticationService(JWTUtil jwtUtil, UserRepository userRepository, Generator generator) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.generator = generator;
    }

    public AuthenticationResponse authenticate(String email, String password) {
        if (!email.isBlank() || !password.isBlank()) {
            Optional<User> savedUser = userRepository.findByEmail(email);
            if (savedUser.isPresent()) {
                if (savedUser.get().getPassword().equals(password)) {
                    return new AuthenticationResponse("0", jwtUtil.generateToken(savedUser.get().getUserid()));
            } else {
                    return new AuthenticationResponse("1", "Invalid Credentials");
                }
            } else {
                return new AuthenticationResponse("1", "User does not exist");
            }
        } else {
            return new AuthenticationResponse("1", "Credentials can not be empty");
        }
    }

    @Transactional
    public AuthenticationResponse createUser(UserRequest userRequest) {
        // Check if existing
        if (userRepository.findByPhoneNumber(userRequest.getPhoneNumber()).isEmpty() && userRepository.findByEmail(userRequest.getEmail()).isEmpty()) {
            User newUser = new User(generator.userIdGenerator(), userRequest.getFirstName(), userRequest.getMiddleName(), userRequest.getLastName(), userRequest.getPhoneNumber(), userRequest.getEmail(), userRequest.getPassword(), LocalDateTime.now());
            try {
                User savedUser = userRepository.save(newUser);
                if (savedUser.getUserid() != null) {
                    logger.info("User saved successfully: {}", savedUser.convertToDTO(savedUser));
                    return new AuthenticationResponse("0", "User created");
                } else {
                    logger.info("User not added to the database: {}", savedUser.convertToDTO(savedUser));
                    return new AuthenticationResponse("1", "User not created");
                }
            } catch (RuntimeException e) {
                logger.error(e.getMessage());
                return new AuthenticationResponse("1", "User creation failed");
            }
        } else {
            return new AuthenticationResponse("1", "Email/Phone Number already exists");
        }
    }
}
