package com.arslankucukkafa.dev.enoco_case.controller;

import com.arslankucukkafa.dev.enoco_case.service.impl.OrderServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
    private final OrderServiceImpl orderService;

    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/place-order")
    public ResponseEntity<?> getPlaceOrder(){
        return null;
    }

    @GetMapping("/get-order-for-code")
    public ResponseEntity<?> getOrderForCode(){
        return null;
    }

    @GetMapping("/get-all-orders-for-customer")
    public ResponseEntity<?> getAllOrdersForCustomer(){
        return null;
    }

}
