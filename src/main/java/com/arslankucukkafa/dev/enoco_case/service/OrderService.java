package com.arslankucukkafa.dev.enoco_case.service;

import com.arslankucukkafa.dev.enoco_case.model.Order;

import java.util.List;

public interface OrderService {
    // TODO: stockda varsa siparişi oluşturur ve stocktan düşer, yoksa hata döner
    Order placeOrder(Long customerId);

    Order getOrderForCode(Long orderId);

    List<Order> getAllOrdersForCustomer(Long customerId);
}
