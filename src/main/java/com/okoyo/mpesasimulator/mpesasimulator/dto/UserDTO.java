package com.okoyo.mpesasimulator.mpesasimulator.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDTO {
    private String userid;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String creationDate;

    public UserDTO(String userid, String firstName, String middleName, String lastName, String phoneNumber, String email, String creationDate) {
        this.userid = userid;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.creationDate = String.valueOf(creationDate);
    }

}
