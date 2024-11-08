package com.arslankucukkafa.dev.enoco_case.model.dto;

import com.arslankucukkafa.dev.enoco_case.model.Customer;

public class CustomerDto {
    public String username;
    public String password;


    public Customer CustomerDtoToCustomer(){
        Customer customer = new Customer();
        customer.setUsername(this.username);
        customer.setPassword(this.password);
        return customer;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
