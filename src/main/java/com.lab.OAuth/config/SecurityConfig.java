package com.lab.OAuth.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(Customizer.withDefaults()) // Use secure defaults for CSRF
                .authorizeRequests((authz) -> authz
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // Allow public access to static resources
                        .requestMatchers(PathRequest.toH2Console()).permitAll() // Allow access to the H2 console
                        .requestMatchers("/", "/login", "/error").permitAll() // Allow public access to the root and login paths
                        .anyRequest().authenticated()
                )
                .oauth2Login((oauth2) -> oauth2.defaultSuccessUrl("/home", true)) // Configure OAuth2 login
                .sessionManagement((session) -> session
                        .sessionFixation().migrateSession() // Migrate session fixation
                        .maximumSessions(1) // Allow only one session per user
                );
        return http.build();
    }
}