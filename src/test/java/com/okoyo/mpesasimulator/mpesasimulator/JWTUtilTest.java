package com.okoyo.mpesasimulator.mpesasimulator;

import com.okoyo.mpesasimulator.mpesasimulator.components.JWTUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JWTUtilTest {
    @Autowired
    private JWTUtil jwtUtil;

    @Test
    public void testTokenGeneration() {
        String token = jwtUtil.generateToken("admin");
        assertNotNull(token);
        System.out.println("Generated token: " + token);
    }

    @Test
    public void testSubjectExtraction() {
        assertEquals("admin", jwtUtil.getSubject(jwtUtil.generateToken("admin")));
    }

    @Test
    public void testSubjectExtractionWithExpiredToken() {
        String token = jwtUtil.generateToken("admin");
        try {
            Thread.sleep(6000);
            assertThrows(RuntimeException.class, () -> jwtUtil.getSubject(token));
        } catch (InterruptedException e) {
            throw new RuntimeException("Thread Interrupted: " + e.getMessage());
        }
    }

    @Test
    public void testSubjectExtractionWithInvalidToken() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQs";
        assertThrows(RuntimeException.class, () -> jwtUtil.getSubject(token));
    }

    @Test
    public void testCheckExpiryWithInvalidToken() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQs";
        assertThrows(RuntimeException.class, () -> jwtUtil.checkExpiry(token));
    }

    @Test
    public void testTokenExpiry() {
        String token = jwtUtil.generateToken("admin");
        try {
            Thread.sleep(6000);
            assertThrows(RuntimeException.class, () -> jwtUtil.getSubject(token));
            assertThrows(RuntimeException.class, () -> jwtUtil.checkExpiry(token));
            assertThrows(RuntimeException.class, () -> jwtUtil.validateToken(token));
        } catch (InterruptedException e) {
            throw new RuntimeException("Thread Interrupted: " + e.getMessage());
        }
    }

    @Test
    public void testInvalidToken() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQs";
        try {
            Thread.sleep(5000);
            assertThrows(RuntimeException.class, () -> jwtUtil.getSubject(token));
            assertThrows(RuntimeException.class, () -> jwtUtil.checkExpiry(token));
            assertThrows(RuntimeException.class, () -> jwtUtil.validateToken(token));
        } catch (InterruptedException e) {
            throw new RuntimeException("Thread Interrupted: " + e.getMessage());
        }
    }
}
