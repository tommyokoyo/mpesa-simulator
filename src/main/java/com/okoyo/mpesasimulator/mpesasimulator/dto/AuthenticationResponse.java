package com.okoyo.mpesasimulator.mpesasimulator.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthenticationResponse {
    @JsonProperty("status")
    private String status;
    @JsonProperty("message")
    private String message;

    public AuthenticationResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

}
