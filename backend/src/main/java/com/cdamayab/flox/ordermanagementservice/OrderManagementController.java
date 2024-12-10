package com.cdamayab.flox.ordermanagementservice;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderManagementController {

    @Autowired
    private OrderManagementService orderManagementService;

    // CRUD

    @Operation(summary = "Create a new order", description = "Add a new order to the platform")
    @ApiResponse(responseCode = "201", description = "Order created successfully")
    @PostMapping
    public Order createOrder(
            @RequestBody @Schema(description = "Details of the order to create") Order order) {
        return orderManagementService.createOrder(order);
    }

    @Operation(summary = "Retrieve all orders", description = "Fetch a list of all orders in the platform")
    @ApiResponse(responseCode = "200", description = "List of orders retrieved successfully")
    @GetMapping
    public List<Order> getAllOrders() {
        return orderManagementService.getAllOrders();
    }

    @Operation(summary = "Retrieve an order by ID", description = "Fetch details of an order by its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found", content = @Content)
    })
    @GetMapping("/{id}")
    public Order getOrderById(
            @PathVariable @Schema(description = "ID of the order to retrieve", example = "1") Long id) {
        return orderManagementService.getOrderById(id);
    }

    @Operation(summary = "Retrieve orders by customer ID", description = "Fetch all orders placed by a specific customer")
    @ApiResponse(responseCode = "200", description = "Orders retrieved successfully")
    @GetMapping("/customer/{customerId}")
    public List<Order> getOrdersByCustomerId(
            @PathVariable @Schema(description = "Customer ID associated with the orders", example = "12345") Long customerId) {
        return orderManagementService.getOrdersByCustomerId(customerId);
    }

    @Operation(summary = "Retrieve orders by status", description = "Fetch all orders with a specific status")
    @ApiResponse(responseCode = "200", description = "Orders retrieved successfully")
    @GetMapping("/status/{status}")
    public List<Order> getOrdersByStatus(
            @PathVariable @Schema(description = "Status of the orders to retrieve", example = "PENDING") String status) {
        return orderManagementService.getOrdersByStatus(status);
    }

    @Operation(summary = "Update an existing order", description = "Modify the details of an existing order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order updated successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found", content = @Content)
    })
    @PutMapping("/{id}")
    public Order updateOrder(
            @PathVariable @Schema(description = "ID of the order to update", example = "1") Long id,
            @RequestBody @Schema(description = "Updated order details") Order updatedOrder) {
        return orderManagementService.updateOrder(id, updatedOrder);
    }

    @Operation(summary = "Delete an order by ID", description = "Remove an order from the platform by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Order deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(
            @PathVariable @Schema(description = "ID of the order to delete", example = "1") Long id) {
        orderManagementService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

}
