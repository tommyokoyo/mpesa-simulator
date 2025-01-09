package com.okoyo.mpesasimulator.mpesasimulator.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/mpesa-simulator/v1")
public class hello {

    @GetMapping("/hello")
    public ResponseEntity<Map<String, String>> simulatorHello() {
        Map<String, String> responseData = new HashMap<>();
        responseData.put("status", "0");
        responseData.put("message", "simulator hello");

        return ResponseEntity.ok().body(responseData);
    }
}
