package com.arslankucukkafa.dev.enoco_case.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import com.arslankucukkafa.dev.enoco_case.util.EntityListener;

@Entity
@Table(name = "product")
@EntityListeners(EntityListener.class)
public class Product extends BaseEntity {

    private String name;
    private String description;
    private int stock;
    private double price;

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