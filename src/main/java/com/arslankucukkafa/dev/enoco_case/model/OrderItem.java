package com.arslankucukkafa.dev.enoco_case.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;

@Entity
@Table(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long orderItemId;

    @ManyToOne
    public Product product;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    public Cart cart;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    public Order order;
    private int quantity;
    private double totalPrice;
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
        updateTotalPrice();
    }
    public Cart getCart() {
        return cart;
    }
    public void setCart(Cart cart) {
        this.cart = cart;
    }
    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
        updateTotalPrice();
    }
    public double getTotalPrice() {
        return totalPrice;
    }
    public void updateTotalPrice() {
        this.totalPrice = product.getPrice() * quantity;
    }
}
