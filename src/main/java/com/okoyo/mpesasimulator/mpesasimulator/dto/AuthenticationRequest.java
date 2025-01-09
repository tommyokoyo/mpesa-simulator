package com.okoyo.mpesasimulator.mpesasimulator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class AuthenticationRequest {
    @JsonProperty("email")
    private  String email;
    @JsonProperty("password")
    private  String password;

}
