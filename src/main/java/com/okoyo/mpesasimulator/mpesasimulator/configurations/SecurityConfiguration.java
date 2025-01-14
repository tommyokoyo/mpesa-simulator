package com.okoyo.mpesasimulator.mpesasimulator.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class SecurityConfiguration {
    private final AuthenticationFilter authenticationFilter;

    @Autowired
    public SecurityConfiguration(AuthenticationFilter authenticationFilter) {
        this.authenticationFilter = authenticationFilter;
    }
    @Bean
    public SecurityFilterChain configureFilter(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/mpesa-simulator/v1/hello").permitAll()
                        .requestMatchers("mpesa-simulator/v1/callbacks").permitAll()
                        .requestMatchers("/mpesa-simulator/v1/auth/token").permitAll()
                        .requestMatchers("/mpesa-simulator/v1/auth/login").permitAll()
                        .requestMatchers("/mpesa-simulator/v1/auth/add-user").authenticated()
                        .requestMatchers("/mpesa-simulator/v1/transaction/mpesa-express").authenticated()
                        .anyRequest().authenticated())
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
