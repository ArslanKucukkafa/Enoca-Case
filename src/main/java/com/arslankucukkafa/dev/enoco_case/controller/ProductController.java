package com.arslankucukkafa.dev.enoco_case.controller;

import com.arslankucukkafa.dev.enoco_case.model.Product;
import com.arslankucukkafa.dev.enoco_case.model.dto.ProductDto;
import com.arslankucukkafa.dev.enoco_case.service.impl.ProductServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductServiceImpl productService;

    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    // TODO: GetProduct CreateProduct UpdateProduct DeleteProduct eklemeleri yapılacak

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(){
        return null;
    }

    @GetMapping("/ls")
    public ResponseEntity<?> getProducts(){
        List<Product> products = productService.getProducts();
        return new ResponseEntity<>(products, null, 200);
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductDto productDto){
        return new ResponseEntity<>( productService.createProduct(productDto), null, 201);
    }

    // Burda Product'a ait productId dışında uniqe bir alan olmadığı için productId üzerinden işlem yapılacak, Dolayısı ile tüm modeli aldım.
    @PutMapping
    public ResponseEntity<?> updateProduct(@RequestBody ProductDto productDto){
        try {
            return new ResponseEntity<>(productService.updateProduct(productDto), null, 200);
        } catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), null, 404);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@RequestParam Long id){
        try {
            productService.deleteProduct(id);
            return new ResponseEntity<>(null, null, 204);
        } catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), null, 404);
        }
    }
}
