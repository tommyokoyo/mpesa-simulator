package com.okoyo.mpesasimulator.mpesasimulator.components;

import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class Generator {
    public String userIdGenerator() {
        return UUID.randomUUID().toString();
    }

    public String MerchantIdGenerator() {
        String serviceCode = "mpesa-sim";
        int segOne = ThreadLocalRandom.current().nextInt(10000, 99999 + 1);
        int segTwo = ThreadLocalRandom.current().nextInt(10000000, 100000000);
        int segThree = ThreadLocalRandom.current().nextInt(1, 9 + 1);

        return serviceCode + "-" + segOne + "-" + segTwo + "-" + segThree;
    }

    public String CheckoutRequestIdGenerator() {
        String serviceCode = "mpesa-sim";
        int segOne = ThreadLocalRandom.current().nextInt(10000, 99999 + 1);
        int segTwo = ThreadLocalRandom.current().nextInt(10000000, 100000000);

        return serviceCode + "-" + segOne + "-" + segTwo;
    }

    public String transactionRequestIdGenerator() {
        String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder builder = new StringBuilder(10);
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            builder.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        return builder.toString();
    }
}
