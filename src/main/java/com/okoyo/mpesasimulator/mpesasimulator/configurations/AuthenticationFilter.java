package com.okoyo.mpesasimulator.mpesasimulator.configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.okoyo.mpesasimulator.mpesasimulator.components.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;
    private final ObjectMapper mapper;


    @Autowired
    public AuthenticationFilter(JWTUtil jwtUtil, ObjectMapper mapper) {
        this.jwtUtil = jwtUtil;
        this.mapper = mapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            String token = authorization.substring(7);
            try {
                if (jwtUtil.validateToken(token) && SecurityContextHolder.getContext().getAuthentication() == null) {
                    String subject = jwtUtil.getSubject(token);
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(subject, null, null);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                } else {
                    errorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
                errorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Could not process request");
            }
        } else {
            errorResponse(response, HttpServletResponse.SC_FORBIDDEN, "Invalid request");
        }

    }

    private void errorResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json;charset=UTF-8");

        // Build a JSON response
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("status", "1");
        responseData.put("message", message);

        String stringResponse = mapper.writeValueAsString(responseData);
        response.getWriter().write(stringResponse);
        response.getWriter().flush();
    }

}
