package com.arslankucukkafa.dev.enoco_case.controller;

import com.arslankucukkafa.dev.enoco_case.service.impl.OrderServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderServiceImpl orderService;

    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "This endpoint is placing an order for the customer")
    @PostMapping("/{customerId}/place")
    public ResponseEntity<?> getPlaceOrder(@PathVariable Long customerId){
        return new ResponseEntity<>(orderService.placeOrder(customerId), null, HttpStatus.CREATED);
    }

    @Operation(summary = "This endpoint is returning the order for the code")
    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderForCode(@PathVariable Long orderId){
        return new ResponseEntity<>(orderService.getOrderForCode(orderId), null, HttpStatus.OK);
    }

    @Operation(summary = "This endpoint is returning all orders for the customer")
    @GetMapping("{customerId}/all")
    public ResponseEntity<?> getAllOrdersForCustomer(@PathVariable Long customerId){
        return new ResponseEntity<>(orderService.getAllOrdersForCustomer(customerId), null, HttpStatus.OK);
    }

}
