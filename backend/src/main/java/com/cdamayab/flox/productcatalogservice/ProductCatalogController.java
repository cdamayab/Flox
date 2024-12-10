package com.cdamayab.flox.productcatalogservice;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Sort;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductCatalogController {

    @Autowired
    private ProductCatalogService productCatalogService;

    // CRUD

    @Operation(summary = "Create a new product", description = "Add a new product to the catalog.")
    @ApiResponse(responseCode = "201", description = "Product created successfully")
    @PostMapping
    public Product createProduct(
            @RequestBody @Schema(description = "Details of the product to create") Product product) {
        return productCatalogService.createProduct(product);
    }

    @Operation(summary = "Retrieve all products", description = "Fetch a list of all products in the catalog, sorted by a specified direction.")
    @ApiResponse(responseCode = "200", description = "List of products retrieved successfully")
    @GetMapping
    public List<Product> getAllProducts(
            @RequestParam(defaultValue = "ASC") @Schema(description = "Sorting direction: ASC for ascending or DESC for descending", example = "ASC") String direction) {
        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        return productCatalogService.getAllProducts(sortDirection);
    }

    @Operation(summary = "Retrieve a product by ID", description = "Fetch details of a product by its unique ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)
    })
    @GetMapping("/{id}")
    public Product getProductById(
            @PathVariable @Schema(description = "ID of the product to retrieve", example = "1") Long id) {
        return productCatalogService.getProductById(id);
    }

    @Operation(summary = "Retrieve products by category", description = "Fetch all products belonging to a specific category.")
    @ApiResponse(responseCode = "200", description = "List of products retrieved successfully")
    @GetMapping("/category/{category}")
    public List<Product> getProductsByCategory(
            @PathVariable @Schema(description = "Category of the products to retrieve", example = "Electronics") String category) {
        return productCatalogService.getProductsByCategory(category);
    }

    @Operation(summary = "Retrieve products by supplier", description = "Fetch all products supplied by a specific supplier.")
    @ApiResponse(responseCode = "200", description = "List of products retrieved successfully")
    @GetMapping("/supplier/{supplier}")
    public List<Product> getProductsBySupplier(
            @PathVariable @Schema(description = "Supplier name of the products to retrieve", example = "TechSupplier Inc.") String supplier) {
        return productCatalogService.getProductsBySupplier(supplier);
    }

    @Operation(summary = "Retrieve products within a price range", description = "Fetch all products with prices within the specified range.")
    @ApiResponse(responseCode = "200", description = "List of products retrieved successfully")
    @GetMapping("/price-range")
    public List<Product> getProductsByPriceRange(
            @RequestParam @Schema(description = "Minimum price", example = "100.0") Float minPrice,
            @RequestParam @Schema(description = "Maximum price", example = "500.0") Float maxPrice) {
        return productCatalogService.getProductsByPriceRange(minPrice, maxPrice);
    }

    @Operation(summary = "Update an existing product", description = "Modify the details of an existing product.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product updated successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)
    })
    @PutMapping("/{id}")
    public Product updateProduct(
            @PathVariable @Schema(description = "ID of the product to update", example = "1") Long id,
            @RequestBody @Schema(description = "Updated product details") Product product) {
        return productCatalogService.updateProduct(id, product);
    }

    @Operation(summary = "Delete a product by ID", description = "Remove a product from the catalog by its ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable @Schema(description = "ID of the product to delete", example = "1") Long id) {
        productCatalogService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete all products in a specific category", description = "Remove all products belonging to the specified category.")
    @ApiResponse(responseCode = "204", description = "Products deleted successfully")
    @DeleteMapping("/category/{category}")
    public ResponseEntity<Void> deleteProductsByCategory(
            @PathVariable @Schema(description = "Category of the products to delete", example = "Electronics") String category) {
        productCatalogService.deleteProductsByCategory(category);
        return ResponseEntity.noContent().build();
    }
}
