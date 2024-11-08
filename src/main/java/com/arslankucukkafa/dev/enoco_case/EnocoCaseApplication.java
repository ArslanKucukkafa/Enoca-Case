package com.arslankucukkafa.dev.enoco_case;

import com.arslankucukkafa.dev.enoco_case.model.dto.CustomerDto;
import com.arslankucukkafa.dev.enoco_case.model.dto.ProductDto;
import com.arslankucukkafa.dev.enoco_case.service.CustomerService;
import com.arslankucukkafa.dev.enoco_case.service.ProductService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EnocoCaseApplication {

	@Autowired
	private ProductService productService;

	@Autowired
	private CustomerService customerService;

	public static void main(String[] args) {
		SpringApplication.run(EnocoCaseApplication.class, args);
	}

	private void initializeProduct(String name, double price, int stock, String description) {
		ProductDto product = new ProductDto();
		product.setName(name);
		product.setPrice(price);
		product.setStock(stock);
		product.setDescription(description);
		productService.createProduct(product);
	}

	private void initializeCustomer(String username, String password) {
		CustomerDto customer = new CustomerDto();
		customer.setUsername(username);
		customer.setPassword(password);
		customerService.addCustomer(customer);
	}
	@PostConstruct
	public void init() {
		initializeProduct("Macbook", 1, 40, "Apple Macbook Pro");
		initializeProduct("Karpuz", 50.0, 50, "Karpuz Tarlası");
		initializeProduct("Elma", 100.0, 100, "Elma Ağacı");
		initializeProduct("Armut", 200.0, 200, "Armut Ağacı");
		initializeCustomer("Arslan", "123456789");
	}
}
