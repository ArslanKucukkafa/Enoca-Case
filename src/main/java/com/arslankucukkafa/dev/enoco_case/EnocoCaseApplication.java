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

	@PostConstruct
	public void init(){
		ProductDto product = new ProductDto();
		product.setName("Elma");
		product.setPrice(100.0);
		product.setStock(100);
		product.setDescription("Elma Ağacı");

		ProductDto product2 = new ProductDto();
		product2.setName("Armut");
		product2.setPrice(200.0);
		product2.setStock(200);
		product2.setDescription("Armut Ağacı");
		productService.createProduct(product);
		productService.createProduct(product2);

		CustomerDto customer = new CustomerDto();
		customer.setUsername("Arslan");
		customer.setEmail("arslankucukkafa@mgial.com");
		customerService.addCustomer(customer);

	}
}
