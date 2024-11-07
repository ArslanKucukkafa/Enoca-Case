package com.arslankucukkafa.dev.enoco_case.service.impl;

import com.arslankucukkafa.dev.enoco_case.exception.ResourceNotFoundException;
import com.arslankucukkafa.dev.enoco_case.model.Cart;
import com.arslankucukkafa.dev.enoco_case.model.Customer;
import com.arslankucukkafa.dev.enoco_case.model.dto.CustomerDto;
import com.arslankucukkafa.dev.enoco_case.repository.CustomerRepository;
import com.arslankucukkafa.dev.enoco_case.service.CartService;
import com.arslankucukkafa.dev.enoco_case.service.CustomerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository;
    private CartService cartService;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, CartService cartService) {
        this.customerRepository = customerRepository;
        this.cartService = cartService;
    }
    @Override
    @Transactional
    public Customer addCustomer(CustomerDto customerDto) {
        Customer customer = customerDto.CustomerDtoToCustomer();
        try {
            Customer savedCustomer = customerRepository.save(customer);
            cartService.createCart(savedCustomer); //fixme: Bunu try catch içinden çıkarmalıyız. Çünkü cartService zaten try catch içeriyor.
            return savedCustomer;
        } catch (Exception e) {
            throw new RuntimeException("Error while saving customer");
        }
    }

    @Override
    public Customer getCustomer(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }


}
