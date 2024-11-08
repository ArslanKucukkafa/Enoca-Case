package com.arslankucukkafa.dev.enoco_case.service;

import com.arslankucukkafa.dev.enoco_case.model.Customer;
import com.arslankucukkafa.dev.enoco_case.model.dto.CustomerDto;
import java.util.List;

public interface CustomerService {

    Customer addCustomer(CustomerDto customerDto);

    List<Customer> getAllCustomers();

}
