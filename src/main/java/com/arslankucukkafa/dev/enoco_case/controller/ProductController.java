package com.arslankucukkafa.dev.enoco_case.controller;

import com.arslankucukkafa.dev.enoco_case.model.Product;
import com.arslankucukkafa.dev.enoco_case.model.dto.ProductDto;
import com.arslankucukkafa.dev.enoco_case.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // TODO: getProduct implement edilecek
    @Operation(summary = "This endpoint is returning the product")
    @GetMapping("/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable Long productId){
        return new ResponseEntity<>(productService.getProduct(productId), null, 200);
    }

    @Operation(summary = "This endpoint is returning all products")
    @GetMapping
    public ResponseEntity<?> getProducts(){
        List<Product> products = productService.getProducts();
        return new ResponseEntity<>(products, null, 200);
    }

    @Operation(summary = "This endpoint is creating a product")
    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductDto productDto){
        return new ResponseEntity<>( productService.createProduct(productDto), null, 201);
    }

    @Operation(summary = "This endpoint is updating a product")
    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Long productId, @RequestBody ProductDto productDto){
        try {
            return new ResponseEntity<>(productService.updateProduct(productId, productDto), null, 200);
        } catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), null, 404);
        }
    }

    @Operation(summary = "This endpoint is deleting a product")
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId){
        try {
            productService.deleteProduct(productId);
            return new ResponseEntity<>(null, null, 204);
        } catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), null, 404);
        }
    }
}
