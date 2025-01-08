package com.cdamayab.flox.authenticationservice;

import com.cdamayab.flox.authenticationservice.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Configuration class that sets up Spring Security for the application.
 * Includes configuring password encoding, JWT authentication filter, and HTTP security rules.
 */
@Configuration
public class SecurityConfig {

    /**
     * Provides a BCryptPasswordEncoder bean to encode and decode passwords.
     * Uses BCrypt hashing algorithm for password security.
     *
     * @return A PasswordEncoder instance using BCrypt hashing.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Provides a JwtAuthenticationFilter bean to filter incoming requests for JWT authentication.
     * This filter processes JWT tokens in the Authorization header and sets the authentication in the security context.
     *
     * @return A JwtAuthenticationFilter instance.
     */
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    /**
     * Configures the HTTP security settings for the application.
     * It sets up the allowed paths, disables CSRF, and configures the JWT authentication filter
     * to be applied before the UsernamePasswordAuthenticationFilter.
     * It also defines session management to be stateless (no HTTP session used).
     *
     * @param http The HttpSecurity instance used to configure web security.
     * @return A SecurityFilterChain bean that contains the configured HTTP security rules.
     * @throws Exception If an error occurs during the configuration process.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll() 
                .requestMatchers("/v3/api-docs/**").permitAll()
                .requestMatchers("/auth/register").permitAll()
                .requestMatchers("/auth/login").permitAll()
                .requestMatchers("/api/products**").permitAll()
                .requestMatchers("/api/reports**").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
            .sessionManagement(session -> 
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Stateless session
        );
        return http.build();
    }

    /**
     * Configures CORS policies to allow requests from your frontend (localhost:4200).
     * This method sets up the CORS configuration to permit cross-origin requests from 
     * the specified frontend URL, allowing all HTTP methods, headers, and enabling credentials.
     *
     * @return An instance of UrlBasedCorsConfigurationSource that defines the CORS configuration.
     *         This configuration will be applied to all endpoints.
     */
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        
        corsConfig.addAllowedOrigin("http://localhost:4200");   // Allows requests only from localhost:4200 (Angular frontend).
        corsConfig.addAllowedMethod("*");                       // Allows all HTTP methods (GET, POST, PUT, DELETE, etc.).
        corsConfig.addAllowedHeader("*");                       // Allows all headers to be sent with the request.
        corsConfig.setAllowCredentials(true);                   // Allows credentials (cookies, authorization headers, etc.)

        // Register the CORS configuration to apply to all routes in the application.
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);  // Applies the configuration to all paths
        
        return source;
    }

}
