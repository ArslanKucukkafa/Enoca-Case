package com.arslankucukkafa.dev.enoco_case.controller;

import com.arslankucukkafa.dev.enoco_case.model.dto.CustomerDto;
import com.arslankucukkafa.dev.enoco_case.service.impl.CustomerServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerServiceImpl customerService;

    public CustomerController(CustomerServiceImpl customerService) {
        this.customerService = customerService;
    }

    @Operation(summary = "This endpoint is adding a customer")
    @PostMapping
    public ResponseEntity<?> addCustomer(@RequestBody CustomerDto customerDto){
        return new ResponseEntity<>(customerService.addCustomer(customerDto),null, HttpStatus.CREATED);
    }

    @Operation(summary = "This endpoint is returning all customers")
    @GetMapping("/all")
    public ResponseEntity<?> getAllCustomers(){
        return new ResponseEntity<>(customerService.getAllCustomers(),null, HttpStatus.OK);
    }

}
