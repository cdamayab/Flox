package com.cdamayab.flox.authenticationservice;

import com.cdamayab.flox.authenticationservice.model.LoginRequest;
import com.cdamayab.flox.authenticationservice.model.Users;
import com.cdamayab.flox.authenticationservice.repository.UsersRepository;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Operation(summary = "Login a user and generate a JWT token")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login successful and token returned"),
        @ApiResponse(responseCode = "401", description = "Invalid username or password", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) throws Exception{
        String token = authenticationService.authenticate(loginRequest);
        return ResponseEntity.ok(token);
    }

    @Operation(summary = "Logout a user and invalidate the token")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Logout successful"),
        @ApiResponse(responseCode = "400", description = "Invalid token", content = @Content)
    })
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
        authenticationService.logout(token.substring(7));
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User registered successfully"),
        @ApiResponse(responseCode = "400", description = "User already exists o error in form", content = @Content)
    })
    @PostMapping("/register")
    public ResponseEntity<String> register( 
            @Valid @RequestBody @Schema(description = "Request") Users user) throws Exception{

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("User already exists");
        }

        // Encrypt the password 
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        // Save the user in the database
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }

}
