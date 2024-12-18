package com.entain.events_api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);
        http
                .authorizeHttpRequests((authorize) -> {
                    authorize
                            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html")
                            .permitAll();

                    authorize
                            .anyRequest()
                            .authenticated();
                }).httpBasic(Customizer.withDefaults());
        return http.build();
    }
}