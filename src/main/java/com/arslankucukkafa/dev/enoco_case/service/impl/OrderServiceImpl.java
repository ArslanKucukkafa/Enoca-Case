package com.arslankucukkafa.dev.enoco_case.service.impl;

import com.arslankucukkafa.dev.enoco_case.model.Cart;
import com.arslankucukkafa.dev.enoco_case.model.Order;
import com.arslankucukkafa.dev.enoco_case.model.OrderItem;
import com.arslankucukkafa.dev.enoco_case.model.Product;
import com.arslankucukkafa.dev.enoco_case.repository.OrderRepository;
import com.arslankucukkafa.dev.enoco_case.service.CartService;
import com.arslankucukkafa.dev.enoco_case.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl {
    private OrderRepository orderRepository;
    private CartService cartService;
    private ProductService productService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, CartService cartService, ProductService productService) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.productService = productService;
    }

    /*PlaceOrder GetOrderForCode GetAllOrdersForCustomer*/
/*    Order order = cartToOrder(customerId);
        return orderRepository.save(order);*/
    // TODO: stockda varsa siparişi oluşturur ve stocktan düşer, yoksa hata döner
    public Order placeOrder(Long customerId) {
        Cart currentCart = cartService.getCart(customerId);
        Order order = cartToOrder(customerId);
        for (OrderItem item: currentCart.getCartItems()) {
            Product product = productService.getProduct(item.getProduct().getId());
            if (product.getStock() < item.getQuantity()) {
                throw new RuntimeException("Not enough stock for product: " + product.getName());
            } else {
                product.setStock(product.getStock() - item.getQuantity());
                productService.updateStock(product.getStock()-item.getQuantity(), product.getId());
            }
        }
    return orderRepository.save(order);
    }

    private Order cartToOrder(Long customerId) {
        Cart cart = cartService.getCart(customerId);
        Order order = new Order();
        order.setOrderItems(cart.getCartItems());
        order.setCustomer(cart.getCustomer());
        return order;
    }
}
