package com.cdamayab.flox.ordermanagementservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service class for managing Order entities.
 */
@Service
public class OrderManagementService {

    @Autowired
    private OrderRepository orderRepository;

    /**
     * Retrieves all orders from the database.
     *
     * @return a list of all orders
     */
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    /**
     * Retrieves an order by its ID.
     *
     * @param id the ID of the order
     * @return the order with the specified ID
     * @throws IllegalArgumentException if the order does not exist
     */
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + id));
    }

    /**
     * Creates a new order.
     *
     * @param order the order to create
     * @return the created order
     */
    public Order createOrder(Order order) {
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        return orderRepository.save(order);
    }

    /**
     * Updates an existing order.
     *
     * @param id           the ID of the order to update
     * @param updatedOrder the updated order details
     * @return the updated order
     * @throws IllegalArgumentException if the order does not exist
     */
    public Order updateOrder(Long id, Order updatedOrder) {
        Order existingOrder = getOrderById(id);

        if (updatedOrder.getCustomerId() != null) existingOrder.setCustomerId(updatedOrder.getCustomerId());
        if (updatedOrder.getTotalPrice() != null) existingOrder.setTotalPrice(updatedOrder.getTotalPrice());
        if (updatedOrder.getStatus() != null) existingOrder.setStatus(updatedOrder.getStatus());

        existingOrder.setUpdatedAt(LocalDateTime.now());
        return orderRepository.save(existingOrder);
    }

    /**
     * Deletes an order by its ID.
     *
     * @param id the ID of the order to delete
     * @throws IllegalArgumentException if the order does not exist
     */
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new IllegalArgumentException("Order not found with ID: " + id);
        }
        orderRepository.deleteById(id);
    }

    /**
     * Retrieves orders by customer ID.
     *
     * @param customerId the ID of the customer
     * @return a list of orders belonging to the specified customer
     */
    public List<Order> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    /**
     * Retrieves orders by their status.
     *
     * @param status the status of the orders (e.g., "PENDING", "COMPLETED")
     * @return a list of orders with the specified status
     */
    public List<Order> getOrdersByStatus(String status) {
        return orderRepository.findByStatus(status);
    }

}
