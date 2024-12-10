package com.cdamayab.flox.productcatalogservice;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing Product entities in the database.
 * Extends JpaRepository to provide CRUD operations and custom query methods.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Finds all products that belong to a specific category.
     *
     * @param category the category to filter products by
     * @return a list of products in the given category
     */
    List<Product> findByCategory(String category);

    /**
     * Finds all products that have a specific supplier.
     *
     * @param supplier the supplier to filter products by
     * @return a list of products supplied by the given supplier
     */
    List<Product> findBySupplier(String supplier);

    /**
     * Retrieves all products sorted by a specific field.
     *
     * @param sort the sorting criteria (e.g., ascending or descending by price)
     * @return a list of sorted products
     */
    List<Product> findAll(Sort sort);

    /**
     * Finds all products whose price falls within the specified range.
     *
     * @param minPrice the minimum price (inclusive)
     * @param maxPrice the maximum price (inclusive)
     * @return a list of products within the specified price range
     */
    List<Product> findByPriceBetween(Float minPrice, Float maxPrice);

    /**
     * Searches for products by name, ignoring case.
     *
     * @param name the name or partial name to search for
     * @return a list of products whose name contains the specified value
     */
    List<Product> findByNameContainingIgnoreCase(String name);

    /**
     * Finds all products that match both the given category and supplier.
     *
     * @param category the category to filter by
     * @param supplier the supplier to filter by
     * @return a list of products matching the specified category and supplier
     */
    List<Product> findByCategoryAndSupplier(String category, String supplier);

    /**
     * Finds all products whose stock is below a given threshold.
     *
     * @param stock the stock threshold
     * @return a list of products with stock less than the specified value
     */
    List<Product> findByStockLessThan(Float stock);

    /**
     * Deletes all products in the specified category.
     *
     * @param category the category whose products should be deleted
     */
    void deleteByCategory(String category);
}
