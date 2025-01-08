package com.cdamayab.flox.authenticationservice.model;

import com.cdamayab.flox.common.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Schema(description = "Represents a user entity in the system.")
public class Users extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the user.", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(max = 50, message = "Name cannot exceed 50 characters")
    @Schema(description = "User's first name.", example = "John", required = true)
    private String userName;

    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name cannot exceed 50 characters")
    @Schema(description = "User's last name.", example = "Smith", required = true)
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Schema(description = "User's email address.", example = "johndoe@example.com", required = true)
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Schema(description = "User's password.", example = "securePassword123", required = true)
    private String password;

    @NotNull(message = "Status is required")
    @Schema(description = "Current status of the user (e.g., active, inactive).", example = "active", required = true)
    private String userStatus;

    @NotNull(message = "Role is required")
    @Schema(description = "Role assigned to the user (e.g., ADMIN, USER).", example = "USER", required = true)
    private String role;
}
