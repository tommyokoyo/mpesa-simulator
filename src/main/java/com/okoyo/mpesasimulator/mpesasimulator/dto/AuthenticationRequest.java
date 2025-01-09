package com.okoyo.mpesasimulator.mpesasimulator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthenticationRequest {
    @JsonProperty("username")
    private  String username;
    @JsonProperty("password")
    private  String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
