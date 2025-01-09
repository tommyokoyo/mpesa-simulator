package com.okoyo.mpesasimulator.mpesasimulator.repositories;

import com.okoyo.mpesasimulator.mpesasimulator.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserid(String UserId);

    Optional<User> findByPhoneNumber(String phoneNumber);

    Optional<User> findByEmail(String email);

}
