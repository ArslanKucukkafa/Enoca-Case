package com.arslankucukkafa.dev.enoco_case.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;

import java.util.List;

@Entity
@Table(name = "cart")
public class Cart extends BaseEntity {

    @OneToOne(cascade = {CascadeType.REMOVE, CascadeType.MERGE}, orphanRemoval = true)
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
        for (OrderItem item : cartItems) {
            item.setCart(this); // Ensure the cart reference is set
        }
        updateCartTotalPrice();
    }
    public double getCartTotalPrice() {
        return cartTotalPrice;
    }
    public void setCartTotalPrice(double cartTotalPrice) {
        this.cartTotalPrice = cartTotalPrice;
    }

    public void updateCartTotalPrice(){
        double totalPrice = 0;
        for (OrderItem item : cartItems) {
            totalPrice += item.getTotalPrice();
        }
        this.cartTotalPrice = totalPrice;
    }
}
