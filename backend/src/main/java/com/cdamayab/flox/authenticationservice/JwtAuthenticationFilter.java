package com.cdamayab.flox.authenticationservice;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * Filter that processes JWT tokens and sets the authentication in the SecurityContext if valid.
 * This filter will intercept requests and validate the provided JWT token in the Authorization header.
 * If valid, it extracts the username and sets it as the authentication in the security context.
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final String SECRET_KEY = AuthenticationService.SECRET_KEY;

    /**
     * Process/intercept the incoming HTTP request to validate the JWT token.
     * If the Authorization header contains a valid JWT token, it extracts the username and sets
     * the authentication in the SecurityContext.
     *
     * @param request The HTTP request object.
     * @param response The HTTP response object.
     * @param chain The filter chain to continue processing the request.
     * @throws ServletException If an error occurs while processing the request.
     * @throws IOException If an error occurs while processing the request.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String username;

        // if no auth header or is not bearer = continues without no auth
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("JwtAuthenticationFilter:doFilterInternal ------------------USER NULL");
            chain.doFilter(request, response);
            return;
        }

        jwtToken = authHeader.substring(7);

        try {
            // Validate and extract the username from token
            username = extractUsername(jwtToken);

            // Ensure is not existing authentication in the security context
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Aquí puedes verificar más claims o crear tu lista de permisos/roles
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());

                // Set the authentication in the security context
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        } catch (JwtException e) {
            // Token is invalid or expired, throw an exception or proceed to filter (resulting in 403 error).
            throw new ServletException("Token inválido: " + e.getMessage());
        }

        // Continue
        chain.doFilter(request, response);
    }

    /**
     * Extracts the username from the provided JWT token.
     * 
     * @param token The JWT token from which the username will be extracted.
     * @return The username (subject) contained in the JWT token.
     */
    private String extractUsername(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
