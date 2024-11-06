package com.arslankucukkafa.dev.enoco_case.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "cart")
public class Cart extends BaseEntity {
    @OneToOne(cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    public Customer customer;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "cart_order_items",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "order_item_id")
    )
    public List<OrderItem>cartItems;
    public double cartTotalPrice;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public List<OrderItem> getCartItems() {
        return cartItems;
    }
    public void setCartItems(List<OrderItem> cartItems) {
        this.cartItems = cartItems;
        this.cartTotalPrice = calculateTotalPrice();
    }
    public double getCartTotalPrice() {
        return cartTotalPrice;
    }
    public void setCartTotalPrice(double cartTotalPrice) {
        this.cartTotalPrice = cartTotalPrice;
    }

    public double calculateTotalPrice(){
        double totalPrice = 0;
        for (OrderItem item : cartItems) {
            totalPrice += item.getTotalPrice();
        }
        return totalPrice;
    }
}
