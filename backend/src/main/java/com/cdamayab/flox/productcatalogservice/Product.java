package com.cdamayab.flox.productcatalogservice;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Schema(description = "Represents a product in the catalog, including details like stock, price, and supplier information.")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the product", example = "101")
    private Long id;

    @Schema(description = "Name of the product", example = "Wireless Headphones")
    private String name;

    @Schema(description = "Detailed description of the product", example = "High-quality wireless headphones with noise cancellation.")
    private String description;

    @Schema(description = "Stock available for the product", example = "150")
    private Float stock;

    @Schema(description = "Price of the product in local currency", example = "5000.00")
    private Float price;

    @Schema(description = "Name of the supplier providing the product", example = "Global Tech Supplies")
    private String supplier;

    @Schema(description = "Category under which the product is classified", example = "Audio Equipment")
    private String category;

    // Getters and Setters

    public Long getId()             { return id; }
    public String getName()         { return name; }
    public String getDescription()  { return description; }
    public Float getStock()         { return stock; }
    public Float getPrice()         { return price; }
    public String getSupplier()     { return supplier; }
    public String getCategory()     { return category; }

    public void setId(Long id)                      { this.id = id; }
    public void setName(String name)                { this.name = name; }
    public void setDescription(String description)  { this.description = description; }
    public void setStock(Float stock)               { this.stock = stock; }
    public void setPrice(Float price)               { this.price = price; }
    public void setSupplier(String supplier)        { this.supplier = supplier; }
    public void setCategory(String category)        { this.category = category; }
}
