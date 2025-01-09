package com.okoyo.mpesasimulator.mpesasimulator.entities;

import com.okoyo.mpesasimulator.mpesasimulator.dto.UserDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Entity
@Table(name = "Users")
public class User {
    @Setter
    @Id
    @Column(name = "UserId")
    private String userid;
    @Setter
    @Column(name = "FirstName")
    private String firstName;
    @Setter
    @Column(name = "MiddleName")
    private String middleName;
    @Setter
    @Column(name = "LastName")
    private String lastName;
    @Setter
    @Column(name = "PhoneNumber")
    private String phoneNumber;
    @Setter
    @Column(name = "Email")
    private String email;
    @Setter
    @Column(name = "Password")
    private String password;
    @Column(name = "CreatedAt")
    private String creationDate;

    public User(String userid, String firstName, String middleName, String lastName, String phoneNumber, String email, String password, LocalDateTime creationDate) {
        this.userid = userid;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.creationDate = String.valueOf(creationDate);
    }

    public User() {
    }

    public UserDTO convertToDTO(User savedUser) {
        return new UserDTO(savedUser.getUserid(), savedUser.getFirstName(), savedUser.getMiddleName(), savedUser.getLastName(), savedUser.getPhoneNumber(), savedUser.getEmail(), savedUser.getCreationDate());
    }
}
