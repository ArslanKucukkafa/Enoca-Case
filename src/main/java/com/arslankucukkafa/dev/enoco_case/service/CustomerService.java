package com.arslankucukkafa.dev.enoco_case.service;

import com.arslankucukkafa.dev.enoco_case.model.Customer;
import com.arslankucukkafa.dev.enoco_case.model.dto.CustomerDto;
import java.util.List;

public interface CustomerService {

    public Customer addCustomer(CustomerDto customerDto);

    public Customer getCustomer(Long id);

    public List<Customer> getAllCustomers();

}
