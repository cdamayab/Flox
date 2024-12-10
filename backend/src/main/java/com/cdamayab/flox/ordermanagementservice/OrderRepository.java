package com.cdamayab.flox.ordermanagementservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing Order entities.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Finds orders by the customer ID.
     *
     * @param customerId the ID of the customer
     * @return a list of orders belonging to the specified customer
     */
    List<Order> findByCustomerId(Long customerId);

    /**
     * Finds orders by their status.
     *
     * @param status the status of the orders (e.g., "PENDING", "COMPLETED")
     * @return a list of orders with the specified status
     */
    List<Order> findByStatus(String status);

    /**
     * Finds orders within a specific total price range.
     *
     * @param minPrice the minimum price of the orders
     * @param maxPrice the maximum price of the orders
     * @return a list of orders within the specified price range
     */
    List<Order> findByTotalPriceBetween(Double minPrice, Double maxPrice);
}
