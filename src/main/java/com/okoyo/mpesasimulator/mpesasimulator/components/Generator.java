package com.okoyo.mpesasimulator.mpesasimulator.components;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Generator {
    public String userIdGenerator() {
        return UUID.randomUUID().toString();
    }
}
