package com.arslankucukkafa.dev.enoco_case.model.dto;

import com.arslankucukkafa.dev.enoco_case.model.Customer;

public class CustomerDto {
    public String username;
    public String email;

    public Customer CustomerDtoToCustomer(){
        Customer customer = new Customer();
        customer.setUsername(this.username);
        customer.setEmail(this.email);
        return customer;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
