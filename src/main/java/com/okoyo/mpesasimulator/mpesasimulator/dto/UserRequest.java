package com.okoyo.mpesasimulator.mpesasimulator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class UserRequest {
    @JsonProperty("firstname")
    private String firstName;
    @JsonProperty("middlename")
    private String middleName;
    @JsonProperty("lastname")
    private String lastName;
    @JsonProperty("phonenumber")
    private String phoneNumber;
    @JsonProperty("email")
    private String email;
    @JsonProperty("password")
    private String password;

}
