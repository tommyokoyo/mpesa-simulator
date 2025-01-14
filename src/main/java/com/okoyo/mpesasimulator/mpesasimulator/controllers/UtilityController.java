package com.okoyo.mpesasimulator.mpesasimulator.controllers;

import com.okoyo.mpesasimulator.mpesasimulator.dto.MpesaExpressCallbackDTO;
import com.okoyo.mpesasimulator.mpesasimulator.dto.MpesaExpressCallbackResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/mpesa-simulator/v1")
public class UtilityController {

    private static final Logger logger = LoggerFactory.getLogger(UtilityController.class);

    @GetMapping("/hello")
    public ResponseEntity<Map<String, String>> simulatorHello() {
        Map<String, String> responseData = new HashMap<>();
        responseData.put("status", "0");
        responseData.put("message", "simulator hello");

        return ResponseEntity.ok().body(responseData);
    }

    @GetMapping("/callbacks")
    public ResponseEntity<MpesaExpressCallbackResponse> simulatorCallbacks(@RequestBody MpesaExpressCallbackDTO mpesaExpressCallbackDTO) {
        logger.info("Callback received: {}", mpesaExpressCallbackDTO.toString());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new MpesaExpressCallbackResponse("0", "Message received"));
    }
}
