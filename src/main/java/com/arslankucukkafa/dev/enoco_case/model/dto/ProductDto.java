package com.arslankucukkafa.dev.enoco_case.model.dto;

import com.arslankucukkafa.dev.enoco_case.model.Product;
import jakarta.annotation.Nullable;

public class ProductDto {

    // Product update ederken id fieldı gerektiği için Nullable olarak tanımladım. Create ederkende null olabilir.
    @Nullable
    public Long id;
    public String name;
    public String description;
    public int stock;
    public double price;

    public Product productDtoToProduct(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setStock(productDto.getStock());
        product.setPrice(productDto.getPrice());
        return product;
    }

    @Nullable
    public Long getId() {
        return id;
    }

    public void setId(@Nullable Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
