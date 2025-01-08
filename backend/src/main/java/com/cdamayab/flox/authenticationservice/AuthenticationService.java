package com.cdamayab.flox.authenticationservice;

import com.cdamayab.flox.authenticationservice.model.ActiveSessions;
import com.cdamayab.flox.authenticationservice.model.LoginRequest;
import com.cdamayab.flox.authenticationservice.model.Users;
import com.cdamayab.flox.authenticationservice.repository.ActiveSessionsRepository;
import com.cdamayab.flox.authenticationservice.repository.UsersRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
public class AuthenticationService {

    // TODO: Move to application properties
    // @Value("${jwt.secret}")
    public static final String SECRET_KEY = "A1B2C3D4E5F6G7H8I9J0K1L2M3N4O5P6";

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private ActiveSessionsRepository activeSessionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Authenticates a user by validating their username and password.
     * 
     * @param loginRequest Contains the username and password.
     * @return A JWT token if authentication is successful.
     * @throws Exception if the user is not found or the password is invalid.
     */
    public String authenticate(LoginRequest loginRequest) throws Exception {
        // Find user in bd
        Optional<Users> userOpt = userRepository.findByUserName(loginRequest.getUsername());
        if (!userOpt.isPresent()) {
            throw new Exception("User not found");
        }
        
        Users user = userOpt.get();
        
        // Compare pass
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new Exception("Invalid password");
        }
        
        // Gen token and save session
        return generateToken(user.getUserName());
    }


    /**
     * Generate a JWT token for a given username.
     * 
     * @param username The username to generate the token.
     * @return The generated JWT token.
     */
    private String generateToken(String username) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiry = now.plusHours(2);
        
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(expiry.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes())
                .compact();

        // Save session in the database
        ActiveSessions session = new ActiveSessions();
        session.setToken(token);
        session.setUsername(username);
        session.setCreatedAt(now);
        session.setExpiresAt(expiry);
        
        activeSessionRepository.save(session);

        return token;
    }

    /**
     * Validates a JWT token by checking its integrity and existence in the active session repository.
     * 
     * @param token The JWT token to validate.
     * @return True if the token is valid, false otherwise.
     * @throws Exception if the token is invalid or not found in the active session repository.
     */
    public boolean validateToken(String token) throws Exception {
        // Parse and validate token
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();

        String username = claims.getSubject();

        // Check if the token exists in the active session repository
        ActiveSessions session = activeSessionRepository.findByToken(token)
            .orElseThrow(() -> new Exception("Token not found"));

        return session != null && session.getUsername().equals(username);
    }

    /**
     * Logs out a user by deleting their active session using the provided token.
     * 
     * @param token The JWT token associated with the user's session.
     */
    public void logout(String token) {
        activeSessionRepository.deleteByToken(token);
    }
}
