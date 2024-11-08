package com.arslankucukkafa.dev.enoco_case.controller;

import com.arslankucukkafa.dev.enoco_case.model.dto.CustomerDto;
import com.arslankucukkafa.dev.enoco_case.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
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
