package com.cdamayab.flox.ordermanagementservice;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Schema(description = "Represents an order in the system")
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the order", example = "1")
    private Long id;

    @Schema(description = "ID of the customer placing the order", example = "12345")
    private Long customerId;

    @Schema(description = "Total price of the order", example = "350000.00")
    private Double totalPrice;

    @Schema(description = "Status of the order", example = "PENDING")
    private String status;

    @Schema(description = "Timestamp when the order was created", example = "2024-12-10T10:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "Timestamp when the order was last updated", example = "2024-12-10T12:00:00")
    private LocalDateTime updatedAt;

    // Getters
    public Long getId()                   { return id; }
    public Long getCustomerId()           { return customerId; }
    public Double getTotalPrice()         { return totalPrice; }
    public String getStatus()             { return status; }
    public LocalDateTime getCreatedAt()   { return createdAt; }
    public LocalDateTime getUpdatedAt()   { return updatedAt; }

    // Setters
    public void setId(Long id)                        { this.id = id; }
    public void setCustomerId(Long customerId)        { this.customerId = customerId; }
    public void setTotalPrice(Double totalPrice)      { this.totalPrice = totalPrice; }
    public void setStatus(String status)             { this.status = status; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
