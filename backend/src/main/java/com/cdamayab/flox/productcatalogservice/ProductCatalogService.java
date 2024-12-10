package com.cdamayab.flox.productcatalogservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.cdamayab.flox.common.*;

import java.util.List;

@Service
public class ProductCatalogService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Retrieve all products with optional sorting by price.
     * 
     * @param direction Sorting direction (ASC or DESC).
     * @return List of all products sorted as specified.
     */
    public List<Product> getAllProducts(Sort.Direction direction) {
        return productRepository.findAll(Sort.by(direction, "price"));
    }

    /**
     * Retrieve a product by its ID with error handling.
     * 
     * @param id Unique identifier of the product.
     * @return The product with the specified ID.
     * @throws ProductNotFoundException If the product does not exist.
     */
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));
    }

    /**
     * Create a new product with validations.
     * 
     * @param product Product details to save.
     * @return The newly created product.
     */
    public Product createProduct(Product product) {
        validateProduct(product); // Validate product details before saving
        return productRepository.save(product);
    }

    /**
     * Update an existing product with detailed field-level updates.
     * 
     * @param id Unique identifier of the product to update.
     * @param updatedProduct Updated product details.
     * @return The updated product.
     * @throws ProductNotFoundException If the product does not exist.
     */
    public Product updateProduct(Long id, Product updatedProduct) {
        Product existingProduct = getProductById(id); // Ensure the product exists

        // Update fields only if the new value is provided
        if (updatedProduct.getName()        != null) existingProduct.setName(updatedProduct.getName());
        if (updatedProduct.getDescription() != null) existingProduct.setDescription(updatedProduct.getDescription());
        if (updatedProduct.getStock()       != null) existingProduct.setStock(updatedProduct.getStock());
        if (updatedProduct.getPrice()       != null) existingProduct.setPrice(updatedProduct.getPrice());
        if (updatedProduct.getSupplier()    != null) existingProduct.setSupplier(updatedProduct.getSupplier());
        if (updatedProduct.getCategory()    != null) existingProduct.setCategory(updatedProduct.getCategory());

        return productRepository.save(existingProduct);
    }

    /**
     * Delete a product by its ID with validation.
     * 
     * @param id Unique identifier of the product to delete.
     * @throws ProductNotFoundException If the product does not exist.
     */
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found with ID: " + id);
        }
        productRepository.deleteById(id);
    }

    /**
     * Retrieve all products belonging to a specific category.
     * 
     * @param category Category to filter by.
     * @return List of products in the specified category.
     */
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    /**
     * Retrieve all products supplied by a specific supplier.
     * 
     * @param supplier Supplier to filter by.
     * @return List of products supplied by the specified supplier.
     */
    public List<Product> getProductsBySupplier(String supplier) {
        return productRepository.findBySupplier(supplier);
    }

    /**
     * Retrieve all products sorted by price in the specified direction.
     * 
     * @param direction Sorting direction (ASC or DESC).
     * @return List of products sorted by price.
     */
    public List<Product> getProductsSortedByPrice(Sort.Direction direction) {
        return productRepository.findAll(Sort.by(direction, "price"));
    }

    /**
     * Retrieve products within a specified price range.
     * 
     * @param minPrice Minimum price (inclusive).
     * @param maxPrice Maximum price (inclusive).
     * @return List of products within the specified price range.
     */
    public List<Product> getProductsByPriceRange(Float minPrice, Float maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

    /**
     * Search for products by name (case-insensitive).
     * 
     * @param name Name or partial name to search for.
     * @return List of products whose name contains the specified value.
     */
    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    /**
     * Delete all products in a specific category.
     * 
     * @param category Category to delete products from.
     */
    public void deleteProductsByCategory(String category) {
        productRepository.deleteByCategory(category);
    }

    /**
     * Count the total number of products in the catalog.
     * 
     * @return Total count of products.
     */
    public long countProducts() {
        return productRepository.count();
    }

    /**
     * Validate product details before saving.
     * Ensures all required fields are present and valid.
     * 
     * @param product Product to validate.
     * @throws IllegalArgumentException If any validation fails.
     */
    private void validateProduct(Product product) {
        if (product.getName() == null || product.getName().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty.");
        }
        if (product.getPrice() == null || product.getPrice() <= 0) {
            throw new IllegalArgumentException("Product price must be greater than zero.");
        }
        if (product.getStock() == null || product.getStock() < 0) {
            throw new IllegalArgumentException("Product stock cannot be negative.");
        }
    }
}
