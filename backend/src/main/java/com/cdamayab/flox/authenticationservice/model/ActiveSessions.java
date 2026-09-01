package com.cdamayab.flox.authenticationservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class ActiveSessions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;

    public ActiveSessions() {} // Default constructor for JPA

    public ActiveSessions(String token, String username, LocalDateTime createdAt, LocalDateTime expiresAt) {
        this.token = token;
        this.username = username;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }

}
